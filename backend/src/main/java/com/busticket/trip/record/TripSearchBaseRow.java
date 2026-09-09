package com.busticket.trip.record;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Ket qua tho cua query 1 (thong tin co ban cua trip, chua co
 * seat types / pickup-dropoff points). Chi dung noi bo trong module
 * trip de lap rap TripSearchResponse, khong expose ra ngoai.
 */
public record TripSearchBaseRow(
        UUID id,
        LocalDateTime departureTime,
        LocalDateTime arrivalTime,
        BigDecimal price,
        String busType,
        String busBrand,
        String licensePlate,
        Integer totalSeats,
        Long bookedSeats,
        String operatorName,
        String originCityName,
        String destinationCityName,
        UUID busId
) {
}
