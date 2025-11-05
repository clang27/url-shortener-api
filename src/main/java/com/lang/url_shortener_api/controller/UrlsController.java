package com.lang.url_shortener_api.controller;


import com.lang.url_shortener_api.model.ShortUrlRequest;
import com.lang.url_shortener_api.service.UrlService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/urls")
@AllArgsConstructor
public class UrlsController {

    private final UrlService urlService;

    /**
     * Idempotent function that will return the same URL whether it exists in DB or not.
     *
     * @param request   The one-to-one mapping between slug and target URL.
     */
    @PutMapping
    public String generateShortUrl(@RequestBody ShortUrlRequest request) {
        return urlService.getOrCreateShortUrl(request.targetUrl());
    }
}
