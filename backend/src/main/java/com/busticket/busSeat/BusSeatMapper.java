package com.busticket.busSeat;

import com.busticket.busseat.dto.BusSeatResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BusSeatMapper {

    @Mapping(source = "bus.id", target = "busId")
    BusSeatResponse toResponse(BusSeat busSeat);

    List<BusSeatResponse> toResponseList(List<BusSeat> busSeats);
}
