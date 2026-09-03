package com.busticket.bus;

import com.busticket.bus.dto.BusResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BusService {

    private final BusRepository busRepository;
    private final BusMapper busMapper;

    public List<BusResponse> getAllBuses() {
        return busMapper.toResponseList(busRepository.findAll());
    }

    public List<BusResponse> getBusesByOperator(UUID operatorId) {
        return busMapper.toResponseList(busRepository.findByOperatorId(operatorId));
    }
}
