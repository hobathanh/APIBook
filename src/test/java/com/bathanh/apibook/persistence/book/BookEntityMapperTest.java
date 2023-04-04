package com.bathanh.apibook.persistence.book;

import org.junit.jupiter.api.Test;

import static com.bathanh.apibook.fakes.BookFakes.buildBookEntities;
import static com.bathanh.apibook.fakes.BookFakes.buildBookEntity;
import static com.bathanh.apibook.persistence.book.BookEntityMapper.toBook;
import static com.bathanh.apibook.persistence.book.BookEntityMapper.toBooks;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BookEntityMapperTest {

    @Test
    void shouldToBook_OK() {
        final var bookEntity = buildBookEntity();
        final var book = toBook(bookEntity);

        assertEquals(bookEntity.getId(), book.getId());
        assertEquals(bookEntity.getTitle(), book.getTitle());
        assertEquals(bookEntity.getAuthor(), book.getAuthor());
        assertEquals(bookEntity.getDescription(), book.getDescription());
        assertEquals(bookEntity.getCreatedAt(), book.getCreatedAt());
        assertEquals(bookEntity.getUpdatedAt(), book.getUpdatedAt());
        assertEquals(bookEntity.getImage(), book.getImage());
        assertEquals(bookEntity.getSubtitle(), book.getSubtitle());
        assertEquals(bookEntity.getPublisher(), book.getPublisher());
        assertEquals(bookEntity.getIsbn13(), book.getIsbn13());
        assertEquals(bookEntity.getPrice(), book.getPrice());
        assertEquals(bookEntity.getYear(), book.getYear());
        assertEquals(bookEntity.getRating(), book.getRating());
        assertEquals(bookEntity.getUserId(), book.getUserId());
    }

    @Test
    void shouldToBookEntity_OK() {
        final var bookEntity = buildBookEntity();
        final var book = toBook(bookEntity);

        assertEquals(book.getId(), bookEntity.getId());
        assertEquals(book.getTitle(), bookEntity.getTitle());
        assertEquals(book.getAuthor(), bookEntity.getAuthor());
        assertEquals(book.getDescription(), bookEntity.getDescription());
        assertEquals(book.getCreatedAt(), bookEntity.getCreatedAt());
        assertEquals(book.getUpdatedAt(), bookEntity.getUpdatedAt());
        assertEquals(book.getImage(), bookEntity.getImage());
        assertEquals(book.getSubtitle(), bookEntity.getSubtitle());
        assertEquals(book.getPublisher(), bookEntity.getPublisher());
        assertEquals(book.getIsbn13(), bookEntity.getIsbn13());
        assertEquals(book.getPrice(), bookEntity.getPrice());
        assertEquals(book.getYear(), bookEntity.getYear());
        assertEquals(book.getRating(), bookEntity.getRating());
        assertEquals(book.getUserId(), bookEntity.getUserId());
    }

    @Test
    void shouldToBooks_OK() {
        final var bookEntities = buildBookEntities();
        final var book = toBooks(bookEntities);

        assertEquals(bookEntities.size(), book.size());
    }
}