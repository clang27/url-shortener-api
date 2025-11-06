package com.lang.url_shortener_api.model;

public record TotalRedirectCountResponse(String slug, String target, Long count) { }
