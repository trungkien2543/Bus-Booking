package com.busticket.routestoptemplate;

import com.busticket.routestoptemplate.dto.RouteStopTemplateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/route-stop-templates")
@RequiredArgsConstructor
public class RouteStopTemplateController {

    private final RouteStopTemplateService routeStopTemplateService;

    @GetMapping
    public List<RouteStopTemplateResponse> getTemplateByRoute(@RequestParam UUID routeId) {
        return routeStopTemplateService.getTemplateByRoute(routeId);
    }
}
