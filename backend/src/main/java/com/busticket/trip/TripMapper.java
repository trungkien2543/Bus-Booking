package com.busticket.trip;

import com.busticket.trip.dto.TripResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TripMapper {

    @Mapping(source = "route.id", target = "routeId")
    @Mapping(source = "bus.id", target = "busId")
    TripResponse toResponse(Trip trip);

    List<TripResponse> toResponseList(List<Trip> trips);
}
