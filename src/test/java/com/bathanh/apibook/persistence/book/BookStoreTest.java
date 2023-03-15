package com.bathanh.apibook.persistence.book;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.bathanh.apibook.fakes.BookFakes.*;
import static com.bathanh.apibook.persistence.book.BookEntityMapper.toBook;
import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookStoreTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookStore bookStore;

    @Test
    void shouldFindAll_OK() {
        final var expected = buildBookEntities();

        when(bookRepository.findAll()).thenReturn(expected);

        final var actual = bookStore.findAll();

        assertEquals(expected.size(), actual.size());

        verify(bookRepository).findAll();
    }

    @Test
    void shouldFindById_OK() {
        final var book = buildBookEntity();
        final var bookOptional = Optional.of(book);
        when(bookRepository.findById(book.getId()))
                .thenReturn(bookOptional);

        assertEquals(bookOptional, bookRepository.findById(book.getId()));

        verify(bookRepository).findById(book.getId());
    }

    @Test
    void shouldSearch_OK() {
        final var expected = buildBookEntities();
        final var book = buildBookEntity();

        when(bookRepository.findAllByTitleOrAuthorOrDescription(book.getTitle()))
                .thenReturn(expected);

        final var actual = bookStore.search(book.getTitle());

        assertEquals(expected.size(), actual.size());

        verify(bookRepository).findAllByTitleOrAuthorOrDescription(book.getTitle());
    }

    @Test
    void shouldCreate_OK() {
        final var book = buildBook();
        final var bookEntity = buildBookEntity();

        when(bookRepository.save(any(BookEntity.class)))
                .thenReturn(bookEntity);

        final var actual = bookStore.create(book);

        assertEquals(bookEntity.getId().toString(), actual.getId().toString());
        assertEquals(bookEntity.getTitle(), actual.getTitle());
        assertEquals(bookEntity.getAuthor(), actual.getAuthor());
        assertEquals(bookEntity.getDescription(), actual.getDescription());
        assertEquals(bookEntity.getCreatedAt(), actual.getCreatedAt());
        assertEquals(bookEntity.getImage(), actual.getImage());
        assertEquals(bookEntity.getUserId(), actual.getUserId());

        verify(bookRepository).save(any(BookEntity.class));
    }

    @Test
    void shouldUpdate_OK() {
        final var bookUpdate = buildBookEntity();

        when(bookRepository.save(any())).thenReturn(bookUpdate);

        final var actual = bookStore.update(toBook(bookUpdate));

        assertEquals(bookUpdate.getId().toString(), actual.getId().toString());
        assertEquals(bookUpdate.getTitle(), actual.getTitle());
        assertEquals(bookUpdate.getAuthor(), actual.getAuthor());
        assertEquals(bookUpdate.getDescription(), actual.getDescription());
        assertEquals(bookUpdate.getUpdatedAt(), actual.getUpdatedAt());
        assertEquals(bookUpdate.getImage(), actual.getImage());
        assertEquals(bookUpdate.getUserId(), actual.getUserId());
    }

    @Test
    void shouldDelete_OK() {
        final var id = randomUUID();

        bookStore.delete(id);

        verify(bookRepository).deleteById(id);
    }
}