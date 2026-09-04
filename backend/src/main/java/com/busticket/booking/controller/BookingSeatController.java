package com.busticket.booking.controller;

import com.busticket.booking.service.BookingSeatService;
import com.busticket.bookingseat.dto.BookingSeatResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/booking-seats")
@RequiredArgsConstructor
public class BookingSeatController {

    private final BookingSeatService bookingSeatService;

    @GetMapping
    public List<BookingSeatResponse> getSeatsByBooking(@RequestParam UUID bookingId) {
        return bookingSeatService.getSeatsByBooking(bookingId);
    }
}
