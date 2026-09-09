package com.busticket.trip;

import com.busticket.trip.dto.TripResponse;
import com.busticket.trip.dto.TripSearchResultResponse;
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
     * GET /api/trips -> toan bo trip (dung de test/debug, xem nhanh du lieu)
     */
    @GetMapping
    public List<TripResponse> getAllTrips() {
        return tripService.getAllTrips();
    }

    /**
     * GET /api/trips/search?originCityId=...&destinationCityId=...&departureDate=2026-09-10
     * -> tim 1 chieu (khong co returnDate)
     *
     * GET /api/trips/search?originCityId=...&destinationCityId=...&departureDate=2026-09-10&returnDate=2026-09-12
     * -> tim khu hoi: chieu di (origin->destination) vao departureDate,
     *    chieu ve (destination->origin) vao returnDate
     */
    @GetMapping("/search")
    public ResponseEntity<TripSearchResultResponse> search(
            @RequestParam UUID originCityId,
            @RequestParam UUID destinationCityId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate departureDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate returnDate
    ) {
        TripSearchResultResponse result = tripService.searchTrips(
                originCityId, destinationCityId, departureDate, returnDate
        );
        return ResponseEntity.ok(result);
    }
}
