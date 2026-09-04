package com.busticket.booking.controller;

import com.busticket.booking.service.BookingService;
import com.busticket.booking.dto.BookingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @GetMapping
    public List<BookingResponse> getBookings(
            @RequestParam(required = false) UUID userId,
            @RequestParam(required = false) UUID tripId
    ) {
        if (userId != null) {
            return bookingService.getBookingsByUser(userId);
        }
        if (tripId != null) {
            return bookingService.getBookingsByTrip(tripId);
        }
        return List.of();
    }
}
