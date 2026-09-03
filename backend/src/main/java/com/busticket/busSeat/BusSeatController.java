package com.busticket.busSeat;

import com.busticket.busseat.dto.BusSeatResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/bus-seats")
@RequiredArgsConstructor
public class BusSeatController {

    private final BusSeatService busSeatService;

    @GetMapping
    public List<BusSeatResponse> getSeatsByBus(@RequestParam UUID busId) {
        return busSeatService.getSeatsByBus(busId);
    }
}
