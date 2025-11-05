package com.lang.url_shortener_api.controller;


import com.lang.url_shortener_api.service.RedirectEventService;
import com.lang.url_shortener_api.service.UrlService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;

@RestController
@RequestMapping
@AllArgsConstructor
public class RedirectController {

    private final UrlService urlService;
    private final RedirectEventService redirectEventService;

    /**
     * Redirects HttpServletRequest if the slug exists in the DB. Otherwise, returns 404.
     *
     * @param slug      32 character, identifying string.
     * @param request   HTTP request that has the User-Agent in the header.
     */
    @GetMapping("{slug}")
    public RedirectView redirect(@PathVariable String slug, HttpServletRequest request) {
        var target = urlService.getSlugBySlug(slug).getTarget();
        redirectEventService.record(target, getUserAgent(request).orElse(""));

        return new RedirectView(target);
    }

    private Optional<String> getUserAgent(HttpServletRequest request) {
        return Optional.of(request.getHeader("User-Agent"));
    }
}
