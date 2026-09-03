package com.busticket.busseat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class BusSeatResponse {
    private UUID id;
    private String seatType;
    private String seatNumber;
    private String seatSide;
    private String position;
    private Integer rowNumber;
    private Integer columnNumber;
    private UUID busId;
}
