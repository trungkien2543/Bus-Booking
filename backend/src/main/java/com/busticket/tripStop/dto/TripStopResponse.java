package com.busticket.tripstop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class TripStopResponse {
    private UUID id;
    private UUID tripId;
    private UUID locationId;
    private String locationName;
    private String locationAddress;
    private String stopType;
    private LocalDateTime stopTime;
    private Integer sequenceOrder;
    private String status;
}
