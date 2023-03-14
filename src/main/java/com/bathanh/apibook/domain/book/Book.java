package com.bathanh.apibook.domain.book;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.With;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@With
@Setter
@Builder
public class Book {

    private UUID id;

    private String title;

    private String author;

    private String description;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String image;

    private UUID userId;
}
