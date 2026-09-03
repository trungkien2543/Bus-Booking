package com.busticket.pickupTrip;

import com.busticket.pickuptrip.dto.PickupTripResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PickupTripMapper {

    @Mapping(source = "city.id", target = "cityId")
    @Mapping(source = "city.name", target = "cityName")
    @Mapping(source = "route.id", target = "routeId")
    PickupTripResponse toResponse(PickupTrip pickupTrip);

    List<PickupTripResponse> toResponseList(List<PickupTrip> pickupTrips);
}
