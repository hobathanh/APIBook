package com.bathanh.apibook.api.book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookResponseDTO {

    private UUID id;
    private String title;
    private String author;
    private String description;
    private Instant createdAt;
    private Instant updatedAt;
    private String image;
    private String subtitle;
    private String publisher;
    private String isbn13;
    private String price;
    private Integer year;
    private Double rating;
    private UUID userId;
}
