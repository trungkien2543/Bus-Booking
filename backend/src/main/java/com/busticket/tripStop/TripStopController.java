package com.busticket.tripstop;

import com.busticket.tripstop.dto.TripStopResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/trip-stops")
@RequiredArgsConstructor
public class TripStopController {

    private final TripStopService tripStopService;

    /**
     * GET /api/trip-stops?tripId=...            -> tat ca diem dung cua trip
     * GET /api/trip-stops?tripId=...&stopType=PICKUP -> chi diem don, sap theo gio
     */
    @GetMapping
    public List<TripStopResponse> getStops(
            @RequestParam UUID tripId,
            @RequestParam(required = false) String stopType
    ) {
        if (stopType != null) {
            return tripStopService.getStopsByTripAndType(tripId, stopType);
        }
        return tripStopService.getStopsByTrip(tripId);
    }
}
