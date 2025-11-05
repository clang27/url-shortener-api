package com.lang.url_shortener_api.service;

import com.lang.url_shortener_api.entity.SlugEntity;
import com.lang.url_shortener_api.model.RedirectCountResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
public class RedirectEventServiceTest extends DatabaseTest {
    @Autowired
    private RedirectEventService redirectEventService;
    @MockitoBean
    private UrlService urlService;

    @Test
    public void recordingARedirectEvent_ShouldSaveTheUserAgent() {
        var slugEntity = getSlugRepository().save(new SlugEntity().setTarget(TARGET).setSlug(SLUG));
        when(urlService.getSlugByTarget(TARGET)).thenReturn(slugEntity);

        redirectEventService.record(TARGET, USER_AGENT);

        var redirectEvents = getRedirectEventsRepository().findAll();

        assertThat(redirectEvents.size()).isEqualTo(1);
        assertThat(redirectEvents.get(0).getUserAgent()).isEqualTo(USER_AGENT);
    }

    @Test
    public void gettingTotalRedirects_ShouldBeTwo_WhenThereAreTwo() {
        var slugEntity = getSlugRepository().save(new SlugEntity().setTarget(TARGET).setSlug(SLUG));
        when(urlService.getSlugByTarget(TARGET)).thenReturn(slugEntity);

        redirectEventService.record(TARGET, USER_AGENT);
        redirectEventService.record(TARGET, USER_AGENT);

        var count = redirectEventService.getAllRedirects();
        assertThat(count.contains(new RedirectCountResponse(SLUG, TARGET, 2L))).isTrue();
    }

    @Test
    public void gettingTodaysRedirects_ShouldBeTwo_WhenThereAreTwo() {
        var slugEntity = getSlugRepository().save(new SlugEntity().setTarget(TARGET).setSlug(SLUG));
        when(urlService.getSlugByTarget(TARGET)).thenReturn(slugEntity);

        redirectEventService.record(TARGET, USER_AGENT);
        redirectEventService.record(TARGET, USER_AGENT);

        var countOne = redirectEventService.getAllRedirectsForDate(LocalDateTime.now());
        var countTwo = redirectEventService.getAllRedirectsForDate(LocalDateTime.now().plusDays(1));
        var countThree = redirectEventService.getAllRedirectsForDate(LocalDateTime.now().minusDays(1));

        assertThat(countOne.contains(new RedirectCountResponse(SLUG, TARGET, 2L))).isTrue();
        assertThat(countTwo.contains(new RedirectCountResponse(SLUG, TARGET, 0L))).isTrue();
        assertThat(countThree.contains(new RedirectCountResponse(SLUG, TARGET, 0L))).isTrue();
    }

    @Test
    public void gettingARangeOfRedirects_ShouldBeTwo_WhenThereAreTwo() {
        var slugEntity = getSlugRepository().save(new SlugEntity().setTarget(TARGET).setSlug(SLUG));
        when(urlService.getSlugByTarget(TARGET)).thenReturn(slugEntity);

        redirectEventService.record(TARGET, USER_AGENT);
        redirectEventService.record(TARGET, USER_AGENT);

        var count = redirectEventService.getAllRedirectsForDateRange(LocalDateTime.now().minusYears(1), LocalDateTime.now().plusYears(1));

        assertThat(count.contains(new RedirectCountResponse(SLUG, TARGET, 2L))).isTrue();
    }
}