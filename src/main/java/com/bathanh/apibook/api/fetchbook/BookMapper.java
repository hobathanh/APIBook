package com.bathanh.apibook.api.fetchbook;

import com.bathanh.apibook.domain.book.Book;

public class BookMapper {

    public static Book toBookFromItemDetail(BookItemDetailDTO bookItemDetailDTO) {
        return Book.builder()
                .title(bookItemDetailDTO.getTitle())
                .subtitle(bookItemDetailDTO.getSubtitle())
                .author(bookItemDetailDTO.getAuthors())
                .publisher(bookItemDetailDTO.getPublisher())
                .isbn13(bookItemDetailDTO.getIsbn13())
                .year(bookItemDetailDTO.getYear())
                .rating(bookItemDetailDTO.getRating())
                .description(bookItemDetailDTO.getDesc())
                .price(bookItemDetailDTO.getPrice())
                .image(bookItemDetailDTO.getImage())
                .build();
    }
}

