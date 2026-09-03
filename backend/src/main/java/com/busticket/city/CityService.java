package com.busticket.city;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CityService {

    private final CityRepository cityRepository;
    private final CityMapper cityMapper;

    public List<CityResponse> getAllCities() {
        return cityMapper.toResponseList(cityRepository.findAllByOrderByNameAsc());
    }
}
