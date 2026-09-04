package com.busticket.payment;

import com.busticket.payment.dto.PaymentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    public Optional<PaymentResponse> getPaymentByBooking(UUID bookingId) {
        return paymentRepository.findByBookingId(bookingId)
                .map(paymentMapper::toResponse);
    }
}
