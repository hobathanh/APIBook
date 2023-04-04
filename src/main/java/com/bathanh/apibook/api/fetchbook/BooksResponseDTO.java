package com.bathanh.apibook.api.fetchbook;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BooksResponseDTO {

    private List<BookItemDTO> books;
}