package com.bathanh.apibook.api.book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookRequestDTO {

    private String title;
    private String author;
    private String description;
    private String image;
    private String subtitle;
    private String publisher;
    private String isbn13;
    private String price;
    private Integer year;
    private Double rating;
}
