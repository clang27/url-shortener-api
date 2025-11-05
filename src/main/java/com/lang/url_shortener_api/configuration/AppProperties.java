package com.lang.url_shortener_api.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
@Data
public class AppProperties {
    private String urlPrefix;
}
