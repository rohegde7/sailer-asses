package com.example.shortenurl.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class UrlAccessLog extends BaseModel{
    @ManyToOne
    // M : 1
    private ShortenedUrl shortenedUrl;

    private long accessedAt;
}
