package com.busticket.trip;

import com.busticket.trip.dto.TripSearchResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface TripRepository extends JpaRepository<Trip, UUID> {

    /**
     * Phan chung cua ca 2 query - tach ra constant de tranh lap code JPQL.
     */
    String SEARCH_SELECT = """
            SELECT new com.busticket.trip.dto.TripSearchResponse(
                t.id,
                t.departureTime,
                t.arrivalTime,
                t.price,
                t.bus.busType,
                t.bus.brand,
                t.bus.licensePlate,
                t.bus.seatCount,
                t.bus.operator.name,
                t.route.originCity.name,
                t.route.destinationCity.name,
                (SELECT COUNT(bs) FROM BookingSeat bs
                    WHERE bs.booking.trip = t
                    AND bs.booking.status <> 'CANCELLED')
            )
            FROM Trip t
            WHERE t.route.originCity.id = :originCityId
              AND t.route.destinationCity.id = :destinationCityId
              AND t.status = 'SCHEDULED'
            """;

    /**
     * Tim trip theo cap thanh pho di - den, KHONG loc theo ngay
     * (dung khi departureDate khong duoc truyen vao).
     */
    @Query(SEARCH_SELECT + " ORDER BY t.departureTime ASC")
    List<TripSearchResponse> searchTripsAllDates(
            @Param("originCityId") UUID originCityId,
            @Param("destinationCityId") UUID destinationCityId
    );

    /**
     * Tim trip theo cap thanh pho di - den, CO loc theo khoang ngay
     * [startOfDay, endOfDay). Dung khi departureDate duoc truyen vao.
     */
    @Query(SEARCH_SELECT + """
              AND t.departureTime >= :startOfDay
              AND t.departureTime < :endOfDay
            ORDER BY t.departureTime ASC
            """)
    List<TripSearchResponse> searchTripsByDate(
            @Param("originCityId") UUID originCityId,
            @Param("destinationCityId") UUID destinationCityId,
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay
    );
}
