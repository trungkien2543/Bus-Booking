package com.busticket.routestoptemplate;

import com.busticket.routestoptemplate.dto.RouteStopTemplateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RouteStopTemplateService {

    private final RouteStopTemplateRepository routeStopTemplateRepository;
    private final RouteStopTemplateMapper routeStopTemplateMapper;

    public List<RouteStopTemplateResponse> getTemplateByRoute(UUID routeId) {
        return routeStopTemplateMapper.toResponseList(
                routeStopTemplateRepository.findByRouteIdOrderBySequenceOrderAsc(routeId)
        );
    }
}
