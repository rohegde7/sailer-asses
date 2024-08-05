package com.example.shortenurl.repositories;

import com.example.shortenurl.models.ShortenedUrl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShortenedUrlRepository extends JpaRepository<ShortenedUrl, Integer> {
    ShortenedUrl findByShortUrl(String shortUrl);
    boolean existsByShortUrl(String shortenedUrl);
}