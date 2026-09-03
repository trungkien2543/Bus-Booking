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
     * Tim cac trip SCHEDULED theo cap thanh pho di - den, trong khoang [startOfDay, endOfDay).
     * Dung khoang thoi gian (thay vi CAST departure_time AS date) de co the tan dung
     * index tren cot departure_time khi ban tu tao index sau nay.
     */
    @Query("""
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
              AND t.departureTime >= :startOfDay
              AND t.departureTime < :endOfDay
              AND t.status = 'SCHEDULED'
            ORDER BY t.departureTime ASC
            """)
    List<TripSearchResponse> searchTrips(
            @Param("originCityId") UUID originCityId,
            @Param("destinationCityId") UUID destinationCityId,
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay
    );
}
