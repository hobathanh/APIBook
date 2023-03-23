package com.bathanh.apibook.domain.book;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Builder
@Getter
@Setter
public class Book {

    private UUID id;

    private String title;

    private String author;

    private String description;

    private Instant createdAt;

    private Instant updatedAt;

    private String image;

    private UUID userId;
}
