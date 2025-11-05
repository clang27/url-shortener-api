package com.lang.url_shortener_api.controller;


import com.lang.url_shortener_api.model.RedirectCountResponse;
import com.lang.url_shortener_api.service.RedirectEventService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@RestController
@RequestMapping(value = "/api/analytics")
@AllArgsConstructor
public class AnalyticsController {

    private final RedirectEventService redirectEventService;

    /**
     * Fetches click counts grouped by slug. If no params passed, will return all counts.
     *
     * @param startDate     If endDate is null, then get counts for one day.
     * @param endDate       Get counts between startDate and endDate.
     */
    @GetMapping("/clicks")
    public Set<RedirectCountResponse> getClickCount(@RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                                    @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        if (Objects.isNull(startDate) && Objects.isNull(endDate)) {
            return redirectEventService.getAllRedirects();
        }
        else if (!Objects.isNull(startDate)) {
            return Objects.isNull(endDate) ?
                    redirectEventService.getAllRedirectsForDate(startDate.atStartOfDay()) :
                    redirectEventService.getAllRedirectsForDateRange(
                            startDate.atStartOfDay(),
                            endDate.atStartOfDay().plusDays(1).minusNanos(1));
        }

        return Set.of();
    }
}
