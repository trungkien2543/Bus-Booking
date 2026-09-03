package com.busticket.route;

import com.busticket.route.dto.RouteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RouteService {

    private final RouteRepository routeRepository;
    private final RouteMapper routeMapper;

    public List<RouteResponse> getAllRoutes() {
        return routeMapper.toResponseList(routeRepository.findAll());
    }

    public List<RouteResponse> findByOriginAndDestination(UUID originCityId, UUID destinationCityId) {
        return routeMapper.toResponseList(
                routeRepository.findByOriginCityIdAndDestinationCityId(originCityId, destinationCityId)
        );
    }
}
