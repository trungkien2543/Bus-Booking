package com.busticket.route;

import com.busticket.route.dto.RouteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/routes")
@RequiredArgsConstructor
public class RouteController {

    private final RouteService routeService;

    @GetMapping
    public List<RouteResponse> getAllRoutes() {
        return routeService.getAllRoutes();
    }

    @GetMapping("/search")
    public List<RouteResponse> search(
            @RequestParam UUID originCityId,
            @RequestParam UUID destinationCityId
    ) {
        return routeService.findByOriginAndDestination(originCityId, destinationCityId);
    }
}
