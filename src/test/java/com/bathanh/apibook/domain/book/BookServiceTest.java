package com.bathanh.apibook.domain.book;

import com.bathanh.apibook.error.BadRequestException;
import com.bathanh.apibook.error.NotFoundException;
import com.bathanh.apibook.persistence.book.BookStore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.bathanh.apibook.fakes.BookFakes.buildBook;
import static com.bathanh.apibook.fakes.BookFakes.buildBooks;
import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookStore bookStore;
    @InjectMocks
    private BookService bookService;

    @Test
    void shouldFindAll_OK() {
        final var expected = buildBooks();

        when(bookStore.findAll()).thenReturn(expected);

        final var actual = bookService.findAll();

        assertEquals(expected.size(), actual.size());
        assertEquals(expected.get(0).getId(), actual.get(0).getId());
        assertEquals(expected.get(0).getTitle(), actual.get(0).getTitle());
        assertEquals(expected.get(0).getAuthor(), actual.get(0).getAuthor());
        assertEquals(expected.get(0).getUserId(), actual.get(0).getUserId());
        assertEquals(expected.get(0).getUpdatedAt(), actual.get(0).getUpdatedAt());
        assertEquals(expected.get(0).getCreatedAt(), actual.get(0).getCreatedAt());
        assertEquals(expected.get(0).getImage(), actual.get(0).getImage());

        verify(bookStore).findAll();
    }

    @Test
    void shouldFindById_OK() {
        final var book = buildBook();

        when(bookStore.findById(book.getId())).thenReturn(Optional.of(book));

        assertEquals(book, bookService.findById(book.getId()));
        verify(bookStore).findById(book.getId());
    }

    @Test
    void shouldFindById_ThrownNotFound() {
        final var uuid = randomUUID();

        when(bookStore.findById(uuid)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> bookService.findById(uuid));
        verify(bookStore).findById(uuid);
    }

    @Test
    void shouldCreate_OK() {
        final var book = buildBook();

        when(bookStore.create(book)).thenReturn(book);

        assertEquals(book, bookService.create(book));
        verify(bookStore).create(book);
    }

    @Test
    void shouldCreate_ThrownBadRequest() {
        final var book = buildBook()
                .withTitle(null);

        assertThrows(BadRequestException.class, () -> bookService.create(book));
    }

    @Test
    void shouldUpdate_OK() {
        final var book = buildBook();
        final var bookUpdate = buildBook();
        bookUpdate.setId(book.getId());
        bookUpdate.setUserId(book.getUserId());

        when(bookStore.findById(book.getId())).thenReturn(Optional.of(book));
        when(bookStore.update(book)).thenReturn(book);

        final var actual = bookService.update(book.getId(), bookUpdate);

        assertEquals(bookUpdate.getId(), actual.getId());
        assertEquals(bookUpdate.getTitle(), actual.getTitle());
        assertEquals(bookUpdate.getAuthor(), actual.getAuthor());
        assertEquals(bookUpdate.getImage(), actual.getImage());
        assertEquals(bookUpdate.getCreatedAt(), actual.getCreatedAt());
        assertEquals(bookUpdate.getDescription(), actual.getDescription());
        assertEquals(bookUpdate.getUserId(), actual.getUserId());

        verify(bookStore).findById(book.getId());
    }

    @Test
    void shouldUpdate_ThrownTitleEmpty() {
        final var id = randomUUID();
        final var bookUpdate = buildBook()
                .withTitle(null);

        assertThrows(BadRequestException.class, () -> bookService.update(id, bookUpdate));
    }

    @Test
    void shouldUpdate_ThrownUserIdEmpty() {
        final var id = randomUUID();
        final var bookUpdate = buildBook()
                .withUserId(null);

        assertThrows(BadRequestException.class, () -> bookService.update(id, bookUpdate));
    }

    @Test
    void shouldUpdate_ThrownAuthorEmpty() {
        final var id = randomUUID();
        final var bookUpdate = buildBook()
                .withAuthor(null);

        assertThrows(BadRequestException.class, () -> bookService.update(id, bookUpdate));
    }

    @Test
    void shouldUpdate_ThrownNotFoundException() {
        final var bookId = randomUUID();
        final var bookUpdate = buildBook();

        when(bookStore.findById(bookId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> bookService.update(bookId, bookUpdate));
        verify(bookStore).findById(bookId);
    }

    @Test
    void shouldDeleteId_OK() {
        final var book = buildBook();

        when(bookStore.findById(book.getId())).thenReturn(Optional.of(book));

        bookService.delete(book.getId());
        verify(bookStore).findById(book.getId());
    }

    @Test
    void shouldDeleteId_ThrownNotFound() {
        final var uuid = randomUUID();

        when(bookStore.findById(uuid)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> bookService.delete(uuid));
        verify(bookStore).findById(uuid);
    }

    @Test
    void shouldSearch_OK() {
        final var book = buildBook();
        final var expected = buildBooks();

        when(bookStore.search(book.getTitle())).thenReturn(expected);

        final var actual = bookService.search(book.getTitle());

        assertEquals(expected.size(), actual.size());

        verify(bookStore).search(book.getTitle());
    }
}