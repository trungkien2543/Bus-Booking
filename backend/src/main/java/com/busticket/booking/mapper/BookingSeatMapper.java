package com.busticket.booking.mapper;

import com.busticket.booking.BookingSeat;
import com.busticket.bookingseat.dto.BookingSeatResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookingSeatMapper {

    @Mapping(source = "booking.id", target = "bookingId")
    @Mapping(source = "busSeat.id", target = "busSeatId")
    BookingSeatResponse toResponse(BookingSeat bookingSeat);

    List<BookingSeatResponse> toResponseList(List<BookingSeat> bookingSeats);
}
