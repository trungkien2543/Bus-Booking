package com.busticket.route;

import com.busticket.route.dto.RouteResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RouteMapper {

    @Mapping(source = "originCity.id", target = "originCityId")
    @Mapping(source = "originCity.name", target = "originCityName")
    @Mapping(source = "destinationCity.id", target = "destinationCityId")
    @Mapping(source = "destinationCity.name", target = "destinationCityName")
    RouteResponse toResponse(Route route);

    List<RouteResponse> toResponseList(List<Route> routes);
}
