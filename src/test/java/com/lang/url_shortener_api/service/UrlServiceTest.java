package com.lang.url_shortener_api.service;

import com.lang.url_shortener_api.configuration.AppProperties;
import com.lang.url_shortener_api.entity.SlugEntity;
import com.lang.url_shortener_api.exception.BadRequestException;
import com.lang.url_shortener_api.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class UrlServiceTest extends DatabaseTest {
    @Autowired
    private UrlService urlService;

    @Autowired
    private AppProperties appProperties;


    @Test
    public void gettingANewShortUrl_ForANewTargetUrl_GeneratesANewSlug() {
        var url = urlService.getOrCreateShortUrl(TARGET);
        var slugs = getSlugRepository().findAll();

        assertThat(slugs.size()).isEqualTo(1);
        assertThat(slugs.get(0).getTarget()).isEqualTo(TARGET);
        assertThat(url).isEqualTo(appProperties.getUrlPrefix() + slugs.get(0).getSlug());
    }

    @Test
    public void gettingANewShortUrl_ForAnOldTargetUrl_FetchesTheOldSlug() {
        getSlugRepository().save(new SlugEntity().setSlug(SLUG).setTarget(TARGET));

        var url = urlService.getOrCreateShortUrl(TARGET);
        var slugs = getSlugRepository().findAll();

        assertThat(slugs.size()).isEqualTo(1);
        assertThat(slugs.get(0).getTarget()).isEqualTo(TARGET);
        assertThat(url).isEqualTo(appProperties.getUrlPrefix() + SLUG);
    }

    @Test
    public void gettingANewShortUrl_ForABadTargetUrl_ThrowsABadRequestException() {
        assertThrows(BadRequestException.class, () -> urlService.getOrCreateShortUrl("bad_target"));
    }

    @Test
    public void gettingASlugEntityByTarget_ShouldReturn_IfItExists() {
        getSlugRepository().save(new SlugEntity().setSlug(SLUG).setTarget(TARGET));

        var slugEntity = urlService.getSlugByTarget(TARGET);

        assertThat(slugEntity.getTarget()).isEqualTo(TARGET);
        assertThat(slugEntity.getSlug()).isEqualTo(SLUG);
    }

    @Test
    public void gettingASlugEntityByTarget_WhenItDoesNotExist_ShouldThrowAnException() {
        assertThrows(NotFoundException.class, () -> urlService.getSlugByTarget("bad_target"));
    }

    @Test
    public void gettingASlugEntityBySlug_ShouldReturn_IfItExists() {
        getSlugRepository().save(new SlugEntity().setSlug(SLUG).setTarget(TARGET));

        var slugEntity = urlService.getSlugBySlug(SLUG);

        assertThat(slugEntity.getTarget()).isEqualTo(TARGET);
        assertThat(slugEntity.getSlug()).isEqualTo(SLUG);
    }

    @Test
    public void gettingASlugEntityBySlug_WhenItDoesNotExist_ShouldThrowAnException(){
        assertThrows(NotFoundException.class, () -> urlService.getSlugBySlug("bad_slug"));
    }
}