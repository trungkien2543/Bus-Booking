package com.busticket.routestoptemplate;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RouteStopTemplateRepository extends JpaRepository<RouteStopTemplate, UUID> {

    List<RouteStopTemplate> findByRouteIdOrderBySequenceOrderAsc(UUID routeId);
}
