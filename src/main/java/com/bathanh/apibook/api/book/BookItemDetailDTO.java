package com.bathanh.apibook.api.book;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookItemDetailDTO {

    private String title;
    private String subtitle;
    private String authors;
    private String publisher;
    private String isbn13;
    private Integer year;
    private Double rating;
    private String desc;
    private String price;
    private String image;
}
