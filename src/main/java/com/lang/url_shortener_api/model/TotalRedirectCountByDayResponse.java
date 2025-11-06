package com.lang.url_shortener_api.model;

public record TotalRedirectCountByDayResponse(String day, String slug, String target, Long count) { }
