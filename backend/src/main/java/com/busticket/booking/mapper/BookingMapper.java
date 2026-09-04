package com.busticket.booking.mapper;

import com.busticket.booking.Booking;
import com.busticket.booking.dto.BookingResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    @Mapping(source = "trip.id", target = "tripId")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "pickUpPoint.id", target = "pickUpPointId")
    @Mapping(source = "dropOffPoint.id", target = "dropOffPointId")
    BookingResponse toResponse(Booking booking);

    List<BookingResponse> toResponseList(List<Booking> bookings);
}
