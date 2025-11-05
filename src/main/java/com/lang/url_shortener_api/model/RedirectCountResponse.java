package com.lang.url_shortener_api.model;

public record RedirectCountResponse(String slug, String target, Long count) { }
