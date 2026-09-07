package com.busticket.routestoptemplate.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class RouteStopTemplateResponse {
    private UUID id;
    private UUID routeId;
    private UUID locationId;
    private String locationName;
    private String stopType;
    private Integer offsetMinutes;
    private Integer sequenceOrder;
}
