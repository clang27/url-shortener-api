package com.lang.url_shortener_api.controller;


import com.lang.url_shortener_api.model.TotalRedirectCountByDayResponse;
import com.lang.url_shortener_api.model.TotalRedirectCountResponse;
import com.lang.url_shortener_api.service.RedirectEventService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping(value = "/api/analytics")
@AllArgsConstructor
@CrossOrigin(origins = "${cross-origin.origins}")
public class AnalyticsController {

    private final RedirectEventService redirectEventService;

    @GetMapping("/clicks")
    public Set<TotalRedirectCountResponse> getTotalClickCount() {
        return redirectEventService.getTotalRedirectCount();
    }

    @GetMapping("/clicks/day")
    public Set<TotalRedirectCountByDayResponse> getClickCountByDay() {
        return redirectEventService.getRedirectCountByDay();
    }
}
