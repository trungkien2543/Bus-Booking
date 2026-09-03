package com.busticket.pickupTrip;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PickupTripRepository extends JpaRepository<PickupTrip, UUID> {

    List<PickupTrip> findByRouteId(UUID routeId);
}
