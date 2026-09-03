package com.busticket.bus;

import com.busticket.bus.dto.BusResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BusMapper {

    @Mapping(source = "operator.id", target = "operatorId")
    @Mapping(source = "operator.name", target = "operatorName")
    BusResponse toResponse(Bus bus);

    List<BusResponse> toResponseList(List<Bus> buses);
}
