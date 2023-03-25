package com.bathanh.apibook.api.book;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookRequestDTO {

    private String title;
    private String author;
    private String description;
    private String image;
}
