package com.busticket.trip.dto;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO tra ve cho API tim kiem chuyen di.
 * Duoc map truc tiep tu JPQL constructor expression trong TripRepository.
 */
@Getter
public class TripSearchResponse {

    private final UUID id;
    private final LocalDateTime departureTime;
    private final LocalDateTime arrivalTime;
    private final BigDecimal price;
    private final String busType;
    private final String busBrand;
    private final String licensePlate;
    private final Integer totalSeats;
    private final Integer availableSeats;
    private final String operatorName;
    private final String originCityName;
    private final String destinationCityName;

    public TripSearchResponse(
            UUID id,
            LocalDateTime departureTime,
            LocalDateTime arrivalTime,
            BigDecimal price,
            String busType,
            String busBrand,
            String licensePlate,
            Integer totalSeats,
            String operatorName,
            String originCityName,
            String destinationCityName,
            Long bookedSeats
    ) {
        this.id = id;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.price = price;
        this.busType = busType;
        this.busBrand = busBrand;
        this.licensePlate = licensePlate;
        this.totalSeats = totalSeats;
        this.operatorName = operatorName;
        this.originCityName = originCityName;
        this.destinationCityName = destinationCityName;
        long booked = bookedSeats == null ? 0L : bookedSeats;
        this.availableSeats = totalSeats == null ? 0 : (int) (totalSeats - booked);
    }
}
