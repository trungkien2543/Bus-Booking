package com.busticket.city;

import org.mapstruct.Mapper;

import java.util.List;

/**
 * MapStruct se tu sinh class CityMapperImpl luc build (annotation processing).
 * componentModel = "spring" giup Spring tu dong tao Bean cho mapper nay,
 * chi can @Autowired / constructor injection binh thuong nhu 1 service.
 */
@Mapper(componentModel = "spring")
public interface CityMapper {

    CityResponse toResponse(City city);

    List<CityResponse> toResponseList(List<City> cities);
}