package com.busticket.trip;

import com.busticket.trip.dto.TripSearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TripService {

    private final TripRepository tripRepository;

    public List<TripSearchResponse> searchTrips(UUID originCityId, UUID destinationCityId, LocalDate departureDate) {
        LocalDateTime startOfDay = departureDate.atStartOfDay();
        LocalDateTime endOfDay = departureDate.plusDays(1).atStartOfDay();
        return tripRepository.searchTrips(originCityId, destinationCityId, startOfDay, endOfDay);
    }
}
