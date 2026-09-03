package com.busticket.city;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class CityResponse {
    private UUID id;
    private String code;
    private String name;
}