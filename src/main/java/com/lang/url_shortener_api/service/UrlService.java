package com.lang.url_shortener_api.service;

import com.lang.url_shortener_api.configuration.AppProperties;
import com.lang.url_shortener_api.entity.SlugEntity;
import com.lang.url_shortener_api.exception.BadRequestException;
import com.lang.url_shortener_api.exception.NotFoundException;
import com.lang.url_shortener_api.repository.SlugRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
@Slf4j
public class UrlService {

    private final AppProperties appProperties;
    private final SlugRepository slugRepository;

    public String getOrCreateShortUrl(String targetUrl) {
        if (!validTargetUrl(targetUrl)) {
            throw new BadRequestException(targetUrl + " is invalid");
        }

        var optionalSlugEntity = slugRepository.findByTarget(targetUrl);

        var slug = optionalSlugEntity.isPresent() ?
                optionalSlugEntity.get().getSlug() :
                slugRepository
                        .save(new SlugEntity()
                                .setSlug(createRandom16CharString())
                                .setTarget(targetUrl))
                        .getSlug();

        return appProperties.getUrlPrefix().concat(slug);
    }

    public SlugEntity getSlugByTarget(String targetUrl) {
        var optionalSlugEntity = slugRepository.findByTarget(targetUrl);

        if (optionalSlugEntity.isEmpty()) {
            throw new NotFoundException(targetUrl + " does not exist.");
        }

        return optionalSlugEntity.get();
    }

    public SlugEntity getSlugBySlug(String slug) {
        var optionalSlugEntity = slugRepository.findBySlug(slug);

        if (optionalSlugEntity.isEmpty()) {
            throw new NotFoundException(slug + " does not exist.");
        }

        return optionalSlugEntity.get();
    }

    private String createRandom16CharString() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 16);
    }

    private boolean validTargetUrl(String url) {
        var regex = "^https?://";
        var pattern = Pattern.compile(regex);
        var matcher = pattern.matcher(url);

        return matcher.find();
    }
}
