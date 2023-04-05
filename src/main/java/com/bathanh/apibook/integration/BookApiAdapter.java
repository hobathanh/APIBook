package com.bathanh.apibook.integration;

import com.bathanh.apibook.api.book.ItBookDetailDTO;
import com.bathanh.apibook.api.book.ItBookItemDTO;
import com.bathanh.apibook.api.book.ItBooksResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BookApiAdapter {
    private final WebClient webClient;

    public List<ItBookItemDTO> fetchNewBooks() {
        return webClient.get()
                .uri("/new")
                .retrieve()
                .bodyToMono(ItBooksResponseDTO.class)
                .block()
                .getBooks();
    }

    public ItBookDetailDTO fetchBookDetail(final String isbn13) {
        return webClient.get()
                .uri("/books/" + isbn13)
                .retrieve()
                .bodyToMono(ItBookDetailDTO.class)
                .block();
    }
}
