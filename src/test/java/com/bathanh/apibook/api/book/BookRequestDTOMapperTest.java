package com.bathanh.apibook.api.book;

import org.junit.jupiter.api.Test;

import static com.bathanh.apibook.api.book.BookDTOMapper.toBookResponseDTO;
import static com.bathanh.apibook.api.book.BookDTOMapper.toBookResponseDTOs;
import static com.bathanh.apibook.fakes.BookFakes.buildBook;
import static com.bathanh.apibook.fakes.BookFakes.buildBooks;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BookRequestDTOMapperTest {

    @Test
    void shouldToBookDTO_OK() {
        final var book = buildBook();
        final var bookDTO = toBookResponseDTO(book);

        assertEquals(book.getId(), bookDTO.getId());
        assertEquals(book.getTitle(), bookDTO.getTitle());
        assertEquals(book.getAuthor(), bookDTO.getAuthor());
        assertEquals(book.getDescription(), bookDTO.getDescription());
        assertEquals(book.getCreatedAt(), bookDTO.getCreatedAt());
        assertEquals(book.getUpdatedAt(), bookDTO.getUpdatedAt());
        assertEquals(book.getImage(), bookDTO.getImage());
        assertEquals(book.getUserId(), bookDTO.getUserId());
        assertEquals(book.getSubtitle(), bookDTO.getSubtitle());
        assertEquals(book.getPublisher(), bookDTO.getPublisher());
        assertEquals(book.getIsbn13(), bookDTO.getIsbn13());
        assertEquals(book.getPrice(), bookDTO.getPrice());
        assertEquals(book.getYear(), bookDTO.getYear());
        assertEquals(book.getRating(), bookDTO.getRating());
    }

    @Test
    void shouldToBook_OK() {
        final var book = buildBook();
        final var bookDTO = toBookResponseDTO(book);

        assertEquals(bookDTO.getId(), book.getId());
        assertEquals(bookDTO.getTitle(), book.getTitle());
        assertEquals(bookDTO.getAuthor(), book.getAuthor());
        assertEquals(bookDTO.getDescription(), book.getDescription());
        assertEquals(bookDTO.getCreatedAt(), book.getCreatedAt());
        assertEquals(bookDTO.getUpdatedAt(), book.getUpdatedAt());
        assertEquals(bookDTO.getImage(), book.getImage());
        assertEquals(bookDTO.getUserId(), book.getUserId());
        assertEquals(bookDTO.getSubtitle(), book.getSubtitle());
        assertEquals(bookDTO.getPublisher(), book.getPublisher());
        assertEquals(bookDTO.getIsbn13(), book.getIsbn13());
        assertEquals(bookDTO.getPrice(), book.getPrice());
        assertEquals(bookDTO.getYear(), book.getYear());
        assertEquals(bookDTO.getRating(), book.getRating());
    }

    @Test
    void shouldToBookDTOs_OK() {
        final var books = buildBooks();

        final var bookDTOs = toBookResponseDTOs(books);
        assertEquals(books.size(), bookDTOs.size());
    }
}