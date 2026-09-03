package com.busticket.operator.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class OperatorResponse {
    private UUID id;
    private String name;
    private String status;
    private String phone;
    private String description;
    private String address;
    private String email;
}
