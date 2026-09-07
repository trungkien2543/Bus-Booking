package com.busticket.location;

import com.busticket.location.dto.LocationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;

    public List<LocationResponse> getAllLocations() {
        return locationMapper.toResponseList(locationRepository.findAll());
    }

    public List<LocationResponse> getLocationsByCity(UUID cityId) {
        return locationMapper.toResponseList(locationRepository.findByCityId(cityId));
    }
}
