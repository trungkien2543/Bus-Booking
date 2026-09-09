package com.busticket.trip;

import com.busticket.trip.dto.TripResponse;
import com.busticket.trip.dto.TripSearchResponse;
import com.busticket.trip.dto.TripSearchResultResponse;
import com.busticket.trip.record.TripSearchBaseRow;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class TripService {

    private final TripRepository tripRepository;
    private final TripMapper tripMapper;

    public List<TripResponse> getAllTrips() {
        return tripMapper.toResponseList(tripRepository.findAll());
    }

    /**
     * departureDate BAT BUOC (ngay di). returnDate OPTIONAL - chi truyen
     * khi nguoi dung muon tim ve khu hoi.
     */
    public TripSearchResultResponse searchTrips(
            UUID originCityId,
            UUID destinationCityId,
            LocalDate departureDate,
            LocalDate returnDate
    ) {
        List<TripSearchResponse> outboundTrips = search(originCityId, destinationCityId, departureDate);

        List<TripSearchResponse> returnTrips = null;
        if (returnDate != null) {
            // Chieu ve: dao nguoc origin/destination
            returnTrips = search(destinationCityId, originCityId, returnDate);
        }

        return new TripSearchResultResponse(outboundTrips, returnTrips);
    }

    /**
     * Chay 3 query rieng biet roi rap lai bang Map, thay vi 1 query
     * khong lo nhieu subquery long nhau - de doc, de debug tung phan.
     */
    private List<TripSearchResponse> search(UUID originCityId, UUID destinationCityId, LocalDate date) {
        // Buoc 1: lay danh sach trip co ban
        List<TripSearchBaseRow> baseRows = tripRepository.searchBaseTrips(
                originCityId, destinationCityId,
                date.atStartOfDay(), date.plusDays(1).atStartOfDay()
        );

        if (baseRows.isEmpty()) {
            return List.of();
        }

        // Buoc 2: lay tong hop ghe theo cac bus lien quan (khong lap lai bus trung nhau)
        // Dùng stream để xử lý từng phần tử trong list baseRow.
        // Dùng map để biến từng phần tử kiểm TripSearchBaseRow thành busId của từng phần tử
        // Loại bỏ phần tử trùng lặp
        // Biến thành list trở lại
        List<UUID> busIds = baseRows.stream().map(TripSearchBaseRow::busId).distinct().toList();


        // Lọc ra danh sách các loại chỗ ngồi theo busID
        // Dùng stream để áp dụng việc tạo thành collection khác cho các phần tử trong list
        // Biến thành Map với key là Bus ID và value là object
        Map<UUID, SeatSummaryProjection> seatSummaryByBus = tripRepository
                .findSeatSummaryByBusIds(busIds).stream()
                .collect(toMapSafe(SeatSummaryProjection::getBusId));

        // Buoc 3: lay tong hop diem don/tra theo cac trip lien quan
        // tuong tu như bước 2
        List<UUID> tripIds = baseRows.stream().map(TripSearchBaseRow::id).toList();

        Map<UUID, StopSummaryProjection> stopSummaryByTrip = tripRepository
                .findStopSummaryByTripIds(tripIds).stream()
                .collect(toMapSafe(StopSummaryProjection::getTripId));

        // Buoc 4: rap lai thanh TripSearchResponse
        return baseRows.stream()
                .map(row -> toResponse(row, seatSummaryByBus.get(row.busId()), stopSummaryByTrip.get(row.id())))
                .toList();
    }

    private TripSearchResponse toResponse(
            TripSearchBaseRow row,
            SeatSummaryProjection seatSummary,
            StopSummaryProjection stopSummary
    ) {
        int totalSeats = row.totalSeats() == null ? 0 : row.totalSeats();
        long booked = row.bookedSeats() == null ? 0L : row.bookedSeats();
        int availableSeats = (int) (totalSeats - booked);

        return new TripSearchResponse(
                row.id(),
                row.departureTime(),
                row.arrivalTime(),
                row.price(),
                row.busType(),
                row.busBrand(),
                row.licensePlate(),
                totalSeats,
                availableSeats,
                row.operatorName(),
                row.originCityName(),
                row.destinationCityName(),
                splitOrEmpty(seatSummary == null ? null : seatSummary.getSeatTypesRaw()),
                splitOrEmpty(seatSummary == null ? null : seatSummary.getSeatSidesRaw()),
                splitOrEmpty(seatSummary == null ? null : seatSummary.getSeatPositionsRaw()),
                splitOrEmpty(stopSummary == null ? null : stopSummary.getPickupPointsRaw()),
                splitOrEmpty(stopSummary == null ? null : stopSummary.getDropoffPointsRaw())
        );
    }

    private List<String> splitOrEmpty(String raw) {
        if (raw == null || raw.isBlank()) {
            return List.of();
        }
        return Arrays.stream(raw.split(","))
                .map(String::trim)
                .distinct()
                .toList();
    }

    /**
     * Helper gom List thanh Map theo key, dung cho ca 2 buoc 2 va 3 o tren.
     */
    private <T> java.util.stream.Collector<T, ?, Map<UUID, T>> toMapSafe(Function<T, UUID> keyExtractor) {
        return java.util.stream.Collectors.toMap(keyExtractor, Function.identity());
    }
}
