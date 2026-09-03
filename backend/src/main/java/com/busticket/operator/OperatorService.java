package com.busticket.operator;

import com.busticket.operator.dto.OperatorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OperatorService {

    private final OperatorRepository operatorRepository;
    private final OperatorMapper operatorMapper;

    public List<OperatorResponse> getAllOperators() {
        return operatorMapper.toResponseList(operatorRepository.findAll());
    }
}
