package com.busticket.tripstop;

import com.busticket.tripstop.dto.TripStopResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TripStopMapper {

    @Mapping(source = "trip.id", target = "tripId")
    @Mapping(source = "location.id", target = "locationId")
    @Mapping(source = "location.name", target = "locationName")
    @Mapping(source = "location.address", target = "locationAddress")
    TripStopResponse toResponse(TripStop tripStop);

    List<TripStopResponse> toResponseList(List<TripStop> tripStops);
}
