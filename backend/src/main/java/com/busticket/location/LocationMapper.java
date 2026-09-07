package com.busticket.location;

import com.busticket.location.dto.LocationResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LocationMapper {

    @Mapping(source = "city.id", target = "cityId")
    @Mapping(source = "city.name", target = "cityName")
    LocationResponse toResponse(Location location);

    List<LocationResponse> toResponseList(List<Location> locations);
}
