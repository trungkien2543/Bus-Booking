package com.busticket.trip;

import com.busticket.trip.dto.TripResponse;
import com.busticket.trip.dto.TripSearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TripService {

    private final TripRepository tripRepository;
    private final TripMapper tripMapper;

    public List<TripResponse> getAllTrips() {
        return tripMapper.toResponseList(tripRepository.findAll());
    }

    /**
     * departureDate la OPTIONAL - truyen null neu chi muon loc theo
     * diem di/diem den, chua chon ngay cu the.
     */
    public List<TripSearchResponse> searchTrips(UUID originCityId, UUID destinationCityId, LocalDate departureDate) {
        if (departureDate == null) {
            return tripRepository.searchTripsAllDates(originCityId, destinationCityId);
        }
        return tripRepository.searchTripsByDate(
                originCityId,
                destinationCityId,
                departureDate.atStartOfDay(),
                departureDate.plusDays(1).atStartOfDay()
        );
    }
}
