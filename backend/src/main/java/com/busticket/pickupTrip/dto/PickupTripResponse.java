package com.busticket.pickuptrip.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class PickupTripResponse {
    private UUID id;
    private String name;
    private String address;
    private String status;
    private String mapUrl;
    private UUID cityId;
    private String cityName;
    private UUID routeId;
}
