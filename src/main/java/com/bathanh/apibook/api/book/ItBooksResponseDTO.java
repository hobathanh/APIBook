package com.bathanh.apibook.api.book;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ItBooksResponseDTO {

    private List<ItBookItemDTO> books;
}