package com.bathanh.apibook.api.fetchbook;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookItemDTO {

    private String title;
    private String subtitle;
    private String isbn13;
    private String price;
    private String image;
}
