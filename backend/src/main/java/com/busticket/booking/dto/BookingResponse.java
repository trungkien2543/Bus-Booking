package com.busticket.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class BookingResponse {
    private UUID id;
    private String status;
    private LocalDateTime expiresAt;
    private UUID tripId;
    private UUID userId;
    private UUID pickUpPointId;
    private UUID dropOffPointId;
}
