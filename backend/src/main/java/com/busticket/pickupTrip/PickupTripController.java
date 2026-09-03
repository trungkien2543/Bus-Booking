package com.busticket.pickupTrip;

import com.busticket.pickuptrip.dto.PickupTripResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/pickup-trips")
@RequiredArgsConstructor
public class PickupTripController {

    private final PickupTripService pickupTripService;

    @GetMapping
    public List<PickupTripResponse> getPickupPointsByRoute(@RequestParam UUID routeId) {
        return pickupTripService.getPickupPointsByRoute(routeId);
    }
}
