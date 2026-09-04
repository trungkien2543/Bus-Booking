package com.busticket.booking.service;

import com.busticket.booking.mapper.BookingMapper;
import com.busticket.booking.repository.BookingRepository;
import com.busticket.booking.dto.BookingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;

    public List<BookingResponse> getBookingsByUser(UUID userId) {
        return bookingMapper.toResponseList(bookingRepository.findByUserId(userId));
    }

    public List<BookingResponse> getBookingsByTrip(UUID tripId) {
        return bookingMapper.toResponseList(bookingRepository.findByTripId(tripId));
    }
}
