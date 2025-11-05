package com.lang.url_shortener_api.service;

import com.lang.url_shortener_api.repository.RedirectEventsRepository;
import com.lang.url_shortener_api.repository.SlugRepository;
import lombok.Getter;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.postgresql.PostgreSQLContainer;

@Getter
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class DatabaseTest {
    public static final String TARGET = "https://www.google.com";
    public static final String USER_AGENT = "userAgent";
    public static final String SLUG = "12345";

    private static final PostgreSQLContainer postgresContainer =
            new PostgreSQLContainer("postgres:17.6")
                .withReuse(true);

    @Autowired
    private RedirectEventsRepository redirectEventsRepository;

    @Autowired
    private SlugRepository slugRepository;

    @BeforeEach
    void before() {
        redirectEventsRepository.deleteAll();
        slugRepository.deleteAll();
    }

    @BeforeAll
    static void beforeAll() {
        postgresContainer.start();
    }

    @AfterAll
    static void afterAll() {
        postgresContainer.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresContainer::getUsername);
        registry.add("spring.datasource.password", postgresContainer::getPassword);
    }
}
