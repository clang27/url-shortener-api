package com.lang.url_shortener_api.repository;

import com.lang.url_shortener_api.entity.SlugEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SlugRepository extends JpaRepository<SlugEntity, Integer> {
    Optional<SlugEntity> findByTarget(String targetUrl);
    Optional<SlugEntity> findBySlug(String slug);
}
