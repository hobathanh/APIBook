package com.bathanh.apibook.integration;

import com.bathanh.apibook.api.book.BookItemDTO;
import com.bathanh.apibook.api.book.BookItemDetailDTO;
import com.bathanh.apibook.api.book.BooksResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BookApiAdapter {
    private final WebClient webClient;

    public List<BookItemDTO> fetchNewBooks() {
        return webClient.get()
                .uri("/new")
                .retrieve()
                .bodyToMono(BooksResponseDTO.class)
                .block()
                .getBooks();
    }

    public BookItemDetailDTO fetchBookDetail(final String isbn13) {
        return webClient.get()
                .uri("/books/" + isbn13)
                .retrieve()
                .bodyToMono(BookItemDetailDTO.class)
                .block();
    }
}
