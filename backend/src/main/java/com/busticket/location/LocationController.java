package com.busticket.location;

import com.busticket.location.dto.LocationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/locations")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @GetMapping
    public List<LocationResponse> getLocations(@RequestParam(required = false) UUID cityId) {
        if (cityId != null) {
            return locationService.getLocationsByCity(cityId);
        }
        return locationService.getAllLocations();
    }
}
