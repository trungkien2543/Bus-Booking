package com.busticket.booking.service;

import com.busticket.booking.mapper.BookingSeatMapper;
import com.busticket.booking.repository.BookingSeatRepository;
import com.busticket.bookingseat.dto.BookingSeatResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingSeatService {

    private final BookingSeatRepository bookingSeatRepository;

    private final BookingSeatMapper bookingSeatMapper;
    

    public List<BookingSeatResponse> getSeatsByBooking(UUID bookingId) {
        return bookingSeatMapper.toResponseList(bookingSeatRepository.findByBookingId(bookingId));
    }
}
