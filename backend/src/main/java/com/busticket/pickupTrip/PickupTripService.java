package com.busticket.pickupTrip;

import com.busticket.pickuptrip.dto.PickupTripResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PickupTripService {

    private final PickupTripRepository pickupTripRepository;
    private final PickupTripMapper pickupTripMapper;

    public List<PickupTripResponse> getPickupPointsByRoute(UUID routeId) {
        return pickupTripMapper.toResponseList(pickupTripRepository.findByRouteId(routeId));
    }
}
