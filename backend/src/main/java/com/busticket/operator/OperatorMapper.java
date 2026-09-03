package com.busticket.operator;

import com.busticket.operator.dto.OperatorResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OperatorMapper {

    OperatorResponse toResponse(Operator operator);

    List<OperatorResponse> toResponseList(List<Operator> operators);
}
