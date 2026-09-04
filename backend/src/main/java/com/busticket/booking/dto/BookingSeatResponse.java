package com.busticket.bookingseat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class BookingSeatResponse {
    private UUID id;
    private BigDecimal price;
    private UUID bookingId;
    private UUID busSeatId;
}
