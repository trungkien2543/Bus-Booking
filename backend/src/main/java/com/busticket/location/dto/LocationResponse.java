package com.busticket.location.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class LocationResponse {
    private UUID id;
    private UUID cityId;
    private String cityName;
    private String name;
    private String address;
    private String mapUrl;
    private String status;
}
