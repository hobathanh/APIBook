package com.bathanh.apibook.persistence.book;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.bathanh.apibook.fakes.BookFakes.*;
import static java.util.UUID.randomUUID;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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

        when(bookRepository.findById(book.getId())).thenReturn(bookOptional);

        final var actual = bookStore.findById(book.getId()).get();
        final var expected = bookOptional.get();

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getTitle(), actual.getTitle());
        assertEquals(expected.getAuthor(), actual.getAuthor());
        assertEquals(expected.getUpdatedAt(), actual.getUpdatedAt());
        assertEquals(expected.getCreatedAt(), actual.getCreatedAt());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getImage(), actual.getImage());

        verify(bookRepository).findById(book.getId());
    }

    @Test
    void shouldFindById_Empty() {
        final var uuid = randomUUID();

        when(bookRepository.findById(uuid)).thenReturn(Optional.empty());

        assertFalse(bookStore.findById(uuid).isPresent());
        verify(bookRepository).findById(uuid);
    }

    @Test
    void shouldFindBookByIsbn13_OK() {
        final var book = buildBookEntity();
        final var bookOptional = Optional.of(book);

        when(bookRepository.findByIsbn13(book.getIsbn13())).thenReturn(bookOptional);

        final var actual = bookStore.findBookByIsbn13(book.getIsbn13()).get();
        final var expected = bookOptional.get();

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getTitle(), actual.getTitle());
        assertEquals(expected.getAuthor(), actual.getAuthor());
        assertEquals(expected.getUpdatedAt(), actual.getUpdatedAt());
        assertEquals(expected.getCreatedAt(), actual.getCreatedAt());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getImage(), actual.getImage());
        assertEquals(expected.getSubtitle(), actual.getSubtitle());
        assertEquals(expected.getPublisher(), actual.getPublisher());
        assertEquals(expected.getIsbn13(), actual.getIsbn13());
        assertEquals(expected.getPrice(), actual.getPrice());
        assertEquals(expected.getYear(), actual.getYear());
        assertEquals(expected.getRating(), actual.getRating());
        assertEquals(expected.getUserId(), actual.getUserId());

        verify(bookRepository).findByIsbn13(book.getIsbn13());
    }

    @Test
    void shouldFindBookByIsbn13_Empty() {
        final var isbn13 = randomNumeric(13);

        when(bookRepository.findByIsbn13(isbn13)).thenReturn(Optional.empty());

        assertFalse(bookStore.findBookByIsbn13(isbn13).isPresent());
        verify(bookRepository).findByIsbn13(isbn13);
    }

    @Test
    void shouldFindByTitleAndAuthor_Ok() {
        final var book = buildBookEntity();
        final var bookOptional = Optional.of(book);
        when(bookRepository.findByTitleAndAuthor(book.getTitle(), book.getAuthor()))
                .thenReturn(bookOptional);

        assertEquals(bookOptional, bookRepository.findByTitleAndAuthor(book.getTitle(), book.getAuthor()));

        verify(bookRepository).findByTitleAndAuthor(book.getTitle(), book.getAuthor());
    }

    @Test
    void shouldFindByTitleAndAuthor_Empty() {
        final var search = randomAlphabetic(6, 10);

        when(bookRepository.findByTitleAndAuthor(search, search)).thenReturn(Optional.empty());

        assertFalse(bookStore.findByTitleAndAuthor(search, search).isPresent());
        verify(bookRepository).findByTitleAndAuthor(search, search);
    }

    @Test
    void shouldFind_OK() {
        final var expected = buildBookEntities();
        final var book = buildBookEntity();

        when(bookRepository.find(book.getTitle())).thenReturn(expected);

        final var actual = bookStore.find(book.getTitle());

        assertEquals(expected.size(), actual.size());

        verify(bookRepository).find(book.getTitle());
    }

    @Test
    void shouldSave_OK() {
        final var bookSave = buildBookEntity();

        when(bookRepository.save(any())).thenReturn(bookSave);

        final var actual = bookStore.save(toBook(bookSave));

        assertEquals(bookSave.getId().toString(), actual.getId().toString());
        assertEquals(bookSave.getTitle(), actual.getTitle());
        assertEquals(bookSave.getAuthor(), actual.getAuthor());
        assertEquals(bookSave.getDescription(), actual.getDescription());
        assertEquals(bookSave.getUpdatedAt(), actual.getUpdatedAt());
        assertEquals(bookSave.getImage(), actual.getImage());
        assertEquals(bookSave.getSubtitle(), actual.getSubtitle());
        assertEquals(bookSave.getPublisher(), actual.getPublisher());
        assertEquals(bookSave.getIsbn13(), actual.getIsbn13());
        assertEquals(bookSave.getPrice(), actual.getPrice());
        assertEquals(bookSave.getYear(), actual.getYear());
        assertEquals(bookSave.getRating(), actual.getRating());
        assertEquals(bookSave.getUserId(), actual.getUserId());
    }

    @Test
    void shouldSaveAll_OK() {
        final var bookEntities = buildBookEntities();

        when(bookRepository.saveAll(any())).thenReturn(bookEntities);

        final var actual = bookStore.saveAll(toBooks(bookEntities));

        assertEquals(bookEntities.size(), actual.size());
    }

    @Test
    void shouldDelete_OK() {
        final var id = randomUUID();

        bookStore.delete(id);

        verify(bookRepository).deleteById(id);
    }
}