package com.lang.url_shortener_api.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.ZonedDateTime;

@Entity
@Table(name = "redirect_events", schema = "url")
@Data
public class RedirectEventsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "slug_id")
    private SlugEntity slug;

    @Column
    private String userAgent;

    @CreationTimestamp
    @Column(updatable = false)
    private ZonedDateTime created;
}
