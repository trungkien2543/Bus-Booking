package com.busticket.tripstop;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TripStopRepository extends JpaRepository<TripStop, UUID> {

    List<TripStop> findByTripIdOrderBySequenceOrderAsc(UUID tripId);

    List<TripStop> findByTripIdAndStopTypeOrderByStopTimeAsc(UUID tripId, String stopType);
}
