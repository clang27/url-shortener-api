package com.lang.url_shortener_api.service;

import com.lang.url_shortener_api.entity.RedirectEventsEntity;
import com.lang.url_shortener_api.model.TotalRedirectCountByDayResponse;
import com.lang.url_shortener_api.model.TotalRedirectCountResponse;
import com.lang.url_shortener_api.repository.RedirectEventsRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class RedirectEventService {

    private final RedirectEventsRepository redirectEventsRepository;
    private final UrlService urlService;

    public void record(String targetUrl, String userAgent) {
        log.info("Adding redirect event for {} from {}", targetUrl, userAgent);

        var eventEntity = new RedirectEventsEntity()
                .setSlug(urlService.getSlugByTarget(targetUrl))
                .setUserAgent(userAgent);

        redirectEventsRepository.save(eventEntity);
    }

    public Set<TotalRedirectCountResponse> getTotalRedirectCount() {
        // This even includes slugs that have a count of 0
        return redirectEventsRepository.getAllRedirectCounts();
    }

    public Set<TotalRedirectCountByDayResponse> getRedirectCountByDay() {
        // Only return the days that have counts
        return redirectEventsRepository.getAllRedirectCountsByDay()
                .stream()
                .filter(response -> !Objects.isNull(response.day()))
                .collect(Collectors.toSet());
    }

    public Set<TotalRedirectCountResponse> getAllRedirectsForDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return redirectEventsRepository.getAllRedirectCountsForDateRange(
                startDate.atZone(ZoneId.systemDefault()),
                endDate.atZone(ZoneId.systemDefault()));
    }

    public Set<TotalRedirectCountResponse> getAllRedirectsForDate(LocalDateTime date) {
        var startDate = date.withHour(0).withMinute(0).withSecond(0).withNano(0);
        var endDate = date.withHour(23).withMinute(59).withSecond(59).withNano(999999999);

        return getAllRedirectsForDateRange(startDate, endDate);
    }
}
