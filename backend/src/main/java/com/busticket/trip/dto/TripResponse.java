package com.busticket.trip.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO don gian, map 1-1 tu Trip entity - dung cho API lay danh sach/xem
 * chi tiet thong thuong. Khac voi TripSearchResponse (gop nhieu bang +
 * tinh availableSeats), cai nay chi tra ve dung field cua Trip.
 */
@Getter
@AllArgsConstructor
public class TripResponse {
    private UUID id;
    private String status;
    private BigDecimal price;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private UUID routeId;
    private UUID busId;
}
