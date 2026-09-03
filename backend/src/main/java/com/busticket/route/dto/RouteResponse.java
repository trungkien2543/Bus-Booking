package com.busticket.route.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class RouteResponse {
    private UUID id;
    private BigDecimal estimateDistance;
    private String status;
    private UUID originCityId;
    private String originCityName;
    private UUID destinationCityId;
    private String destinationCityName;
}
