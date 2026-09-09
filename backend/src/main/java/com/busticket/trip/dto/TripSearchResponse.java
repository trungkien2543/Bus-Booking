package com.busticket.trip.dto;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * DTO tra ve cho API tim kiem chuyen di. Duoc lap rap trong TripService
 * tu TripSearchProjection (ket qua native query) - khong dung JPQL
 * constructor expression nua vi can tra ve List<String> (seatTypes,
 * pickupPoints...) ma JPQL constructor khong ho tro truc tiep.
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

    // Danh sach nhieu gia tri, dung de FE loc: 1 xe co the co nhieu loai ghe,
    // 1 trip co the co nhieu diem don/tra khac nhau
    private final List<String> seatTypes;
    private final List<String> seatSides;
    private final List<String> seatPositions;
    private final List<String> pickupPoints;
    private final List<String> dropoffPoints;

    public TripSearchResponse(
            UUID id,
            LocalDateTime departureTime,
            LocalDateTime arrivalTime,
            BigDecimal price,
            String busType,
            String busBrand,
            String licensePlate,
            Integer totalSeats,
            Integer availableSeats,
            String operatorName,
            String originCityName,
            String destinationCityName,
            List<String> seatTypes,
            List<String> seatSides,
            List<String> seatPositions,
            List<String> pickupPoints,
            List<String> dropoffPoints
    ) {
        this.id = id;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.price = price;
        this.busType = busType;
        this.busBrand = busBrand;
        this.licensePlate = licensePlate;
        this.totalSeats = totalSeats;
        this.availableSeats = availableSeats;
        this.operatorName = operatorName;
        this.originCityName = originCityName;
        this.destinationCityName = destinationCityName;
        this.seatTypes = seatTypes;
        this.seatSides = seatSides;
        this.seatPositions = seatPositions;
        this.pickupPoints = pickupPoints;
        this.dropoffPoints = dropoffPoints;
    }
}
