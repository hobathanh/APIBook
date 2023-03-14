package com.bathanh.apibook.api.book;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class BookResponseDTO {

    private UUID id;

    private String title;

    private String author;

    private String description;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String image;

    private UUID userId;
}
