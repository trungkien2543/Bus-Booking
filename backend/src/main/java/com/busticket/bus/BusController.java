package com.busticket.bus;

import com.busticket.bus.dto.BusResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/buses")
@RequiredArgsConstructor
public class BusController {

    private final BusService busService;

    @GetMapping
    public List<BusResponse> getAllBuses(@RequestParam(required = false) UUID operatorId) {
        if (operatorId != null) {
            return busService.getBusesByOperator(operatorId);
        }
        return busService.getAllBuses();
    }
}
