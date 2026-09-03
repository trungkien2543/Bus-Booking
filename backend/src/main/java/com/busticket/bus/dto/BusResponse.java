package com.busticket.bus.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class BusResponse {
    private UUID id;
    private String busType;
    private Integer seatCount;
    private String licensePlate;
    private String status;
    private String brand;
    private Integer year;
    private UUID operatorId;
    private String operatorName;
}
