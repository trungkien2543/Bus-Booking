package com.busticket.busSeat;

import com.busticket.busseat.dto.BusSeatResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BusSeatService {

    private final BusSeatRepository busSeatRepository;
    private final BusSeatMapper busSeatMapper;

    public List<BusSeatResponse> getSeatsByBus(UUID busId) {
        return busSeatMapper.toResponseList(busSeatRepository.findByBusId(busId));
    }
}
