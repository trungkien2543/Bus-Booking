package com.busticket.route;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RouteRepository extends JpaRepository<Route, UUID> {

    List<Route> findByOriginCityIdAndDestinationCityId(UUID originCityId, UUID destinationCityId);
}
