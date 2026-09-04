package com.busticket.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class PaymentResponse {
    private UUID id;
    private BigDecimal amount;
    private String method;
    private String status;
    private String transactionId;
    private LocalDateTime paidAt;
    private UUID bookingId;
}
