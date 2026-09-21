package com.busticket.trip.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class PopularRouteResponse {
    private UUID routeId;
    private UUID originCityId;
    private String originCityName;
    private UUID destinationCityId;
    private String destinationCityName;
    private String destinationImageUrl; // co the null neu thanh pho chua co anh trong DB
    private Long tripCount;
    private BigDecimal minPrice;
}
