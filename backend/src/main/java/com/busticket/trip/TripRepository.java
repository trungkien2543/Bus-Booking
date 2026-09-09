package com.busticket.trip;

import com.busticket.trip.record.TripSearchBaseRow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface TripRepository extends JpaRepository<Trip, UUID> {

    /**
     * QUERY 1: thong tin co ban cua trip (gan giong ban goc ban dau).
     * Dung JPQL constructor expression - don gian, de doc.
     */
    @Query("""
            SELECT new com.busticket.trip.record.TripSearchBaseRow(
                t.id,
                t.departureTime,
                t.arrivalTime,
                t.price,
                t.bus.busType,
                t.bus.brand,
                t.bus.licensePlate,
                t.bus.seatCount,
                (SELECT COUNT(bs) FROM BookingSeat bs
                    WHERE bs.booking.trip = t
                    AND bs.booking.status <> 'CANCELLED'),
                t.bus.operator.name,
                t.route.originCity.name,
                t.route.destinationCity.name,
                t.bus.id
            )
            FROM Trip t
            WHERE t.route.originCity.id = :originCityId
              AND t.route.destinationCity.id = :destinationCityId
              AND t.departureTime >= :startOfDay
              AND t.departureTime < :endOfDay
              AND t.status = 'SCHEDULED'
            ORDER BY t.departureTime ASC
            """)
    List<TripSearchBaseRow> searchBaseTrips(
            @Param("originCityId") UUID originCityId,
            @Param("destinationCityId") UUID destinationCityId,
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay
    );

    /**
     * QUERY 2: tong hop loai ghe / vi tri ghe theo tung bus.
     * Chi can 1 GROUP BY don gian tren bang bus_seat, khong join gi them.
     */
    @Query(value = """
            SELECT
                bus_id AS busId,
                STRING_AGG(DISTINCT seat_type, ',') AS seatTypesRaw,
                STRING_AGG(DISTINCT seat_side, ',') AS seatSidesRaw,
                STRING_AGG(DISTINCT position, ',') AS seatPositionsRaw
            FROM bus_seat
            WHERE bus_id IN :busIds
            GROUP BY bus_id
            """, nativeQuery = true)
    List<SeatSummaryProjection> findSeatSummaryByBusIds(@Param("busIds") List<UUID> busIds);

    /**
     * QUERY 3: tong hop diem don/tra theo tung trip.
     * Dung CASE WHEN de tach PICKUP/DROPOFF trong 1 lan GROUP BY,
     * thay vi 2 subquery rieng nhu truoc.
     */
    @Query(value = """
            SELECT
                ts.trip_id AS tripId,
                STRING_AGG(DISTINCT CASE WHEN ts.stop_type = 'PICKUP' THEN l.name END, ',') AS pickupPointsRaw,
                STRING_AGG(DISTINCT CASE WHEN ts.stop_type = 'DROPOFF' THEN l.name END, ',') AS dropoffPointsRaw
            FROM trip_stop ts
            JOIN location l ON l.id = ts.location_id
            WHERE ts.trip_id IN :tripIds
            GROUP BY ts.trip_id
            """, nativeQuery = true)
    List<StopSummaryProjection> findStopSummaryByTripIds(@Param("tripIds") List<UUID> tripIds);
}
