package com.bathanh.apibook.integration;

import com.bathanh.apibook.domain.book.Book;

public class ItBookMapper {

    public static Book toBook(ItBookDetailDTO itBookDetailDTO) {
        return Book.builder()
                .title(itBookDetailDTO.getTitle())
                .subtitle(itBookDetailDTO.getSubtitle())
                .author(itBookDetailDTO.getAuthors())
                .publisher(itBookDetailDTO.getPublisher())
                .isbn13(itBookDetailDTO.getIsbn13())
                .year(itBookDetailDTO.getYear())
                .rating(itBookDetailDTO.getRating())
                .description(itBookDetailDTO.getDesc())
                .price(itBookDetailDTO.getPrice())
                .image(itBookDetailDTO.getImage())
                .build();
    }
}

