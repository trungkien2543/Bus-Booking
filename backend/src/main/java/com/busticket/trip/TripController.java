package com.busticket.trip;

import com.busticket.trip.dto.TripSearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/trips")
@RequiredArgsConstructor
public class TripController {

    private final TripService tripService;

    /**
     * GET /api/trips/search?originCityId=...&destinationCityId=...&departureDate=2026-09-10
     */
    @GetMapping("/search")
    public ResponseEntity<List<TripSearchResponse>> search(
            @RequestParam UUID originCityId,
            @RequestParam UUID destinationCityId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate departureDate
    ) {
        List<TripSearchResponse> results = tripService.searchTrips(originCityId, destinationCityId, departureDate);
        return ResponseEntity.ok(results);
    }
}
