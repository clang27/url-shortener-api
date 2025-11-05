package com.lang.url_shortener_api.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.ZonedDateTime;

@Data
@Entity
@Table(name = "slugs", schema = "url")
public class SlugEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String slug;

    @Column
    private String target;

    @CreationTimestamp
    @Column(updatable = false)
    private ZonedDateTime created;
}
