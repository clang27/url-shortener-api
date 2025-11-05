package com.lang.url_shortener_api.repository;

import com.lang.url_shortener_api.entity.RedirectEventsEntity;
import com.lang.url_shortener_api.model.RedirectCountResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.ZonedDateTime;
import java.util.Set;

public interface RedirectEventsRepository extends JpaRepository<RedirectEventsEntity, Integer> {
    @Query(value = """
        SELECT T2.slug, T2.target, COUNT(T1.id) as count
        FROM url.redirect_events T1
        RIGHT JOIN url.slugs T2 ON T1.slug_id = T2.id
        GROUP BY T2.slug, T2.target
    """, nativeQuery = true)
    Set<RedirectCountResponse> getAllRedirectCounts();

    @Query(value = """
        SELECT T2.slug, T2.target, COUNT(T1.id) as count
        FROM (SELECT * FROM url.redirect_events WHERE created > :startDate AND created < :endDate) T1
        RIGHT JOIN url.slugs T2 ON T1.slug_id = T2.id
        GROUP BY T2.slug, T2.target
    """, nativeQuery = true)
    Set<RedirectCountResponse> getAllRedirectCountsForDateRange(@Param("startDate") ZonedDateTime startDate,
                                                                @Param("endDate") ZonedDateTime endDate);
}
