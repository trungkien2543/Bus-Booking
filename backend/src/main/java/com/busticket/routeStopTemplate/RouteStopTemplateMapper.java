package com.busticket.routestoptemplate;

import com.busticket.routestoptemplate.dto.RouteStopTemplateResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RouteStopTemplateMapper {

    @Mapping(source = "route.id", target = "routeId")
    @Mapping(source = "location.id", target = "locationId")
    @Mapping(source = "location.name", target = "locationName")
    RouteStopTemplateResponse toResponse(RouteStopTemplate routeStopTemplate);

    List<RouteStopTemplateResponse> toResponseList(List<RouteStopTemplate> routeStopTemplates);
}
