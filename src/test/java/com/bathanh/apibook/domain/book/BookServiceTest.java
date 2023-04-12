package com.bathanh.apibook.domain.book;

import com.bathanh.apibook.domain.auths.AuthsProvider;
import com.bathanh.apibook.error.BadRequestException;
import com.bathanh.apibook.error.ForbiddenException;
import com.bathanh.apibook.error.NotFoundException;
import com.bathanh.apibook.persistence.book.BookStore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.time.Instant;
import java.util.Optional;

import static com.bathanh.apibook.fakes.BookFakes.buildBook;
import static com.bathanh.apibook.fakes.BookFakes.buildBooks;
import static com.bathanh.apibook.fakes.UserAuthenticationTokenFakes.buildAdmin;
import static com.bathanh.apibook.fakes.UserAuthenticationTokenFakes.buildContributor;
import static java.util.UUID.randomUUID;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookStore bookStore;
    @InjectMocks
    private BookService bookService;
    @Mock
    private AuthsProvider authsProvider;
    @Mock
    private CloudinaryService cloudinaryService;

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
    void shouldFind_OK() {
        final var book = buildBook();
        final var expected = buildBooks();

        when(bookStore.find(book.getTitle())).thenReturn(expected);

        final var actual = bookService.find(book.getTitle());

        assertEquals(expected.size(), actual.size());

        verify(bookStore).find(book.getTitle());
    }

    @Test
    void shouldFindById_ThrownNotFound() {
        final var uuid = randomUUID();

        when(bookStore.findById(uuid)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> bookService.findById(uuid));
        verify(bookStore).findById(uuid);
    }

    @Test
    void shouldFindBookByIsbn13_OK() {
        final var book = buildBook();

        when(bookStore.findBookByIsbn13(book.getIsbn13())).thenReturn(Optional.of(book));

        assertEquals(book, bookService.findBookByIsbn13(book.getIsbn13()));
        verify(bookStore).findBookByIsbn13(book.getIsbn13());
    }

    @Test
    void shouldFindBookByIsbn13_ThrownNotFound() {
        final var isbn13 = randomNumeric(13);

        when(bookStore.findBookByIsbn13(isbn13)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> bookService.findBookByIsbn13(isbn13));
        verify(bookStore).findBookByIsbn13(isbn13);
    }

    @Test
    void shouldCreate_Contributor_OK() {
        final var book = buildBook();

        when(bookStore.save(any(Book.class))).thenReturn(book);
        when(authsProvider.getCurrentUserId()).thenReturn(buildContributor().getUserId());

        final var actual = bookService.create(book);

        assertEquals(book, actual);
        verify(bookStore).save(any(Book.class));
    }

    @Test
    void shouldCreate_Admin_OK() {
        final var book = buildBook();

        when(bookStore.save(any(Book.class))).thenReturn(book);
        when(authsProvider.getCurrentUserId()).thenReturn(buildAdmin().getUserId());

        final var actual = bookService.create(book);

        assertEquals(book, actual);
        verify(bookStore).save(any(Book.class));
    }

    @Test
    void shouldCreate_ThrownBadRequest() {
        final var book = buildBook()
                .withTitle(null);

        assertThrows(BadRequestException.class, () -> bookService.create(book));

        verify(bookStore, never()).save(book);
    }

    @Test
    void shouldCreate_ThrownTitleAndAuthorAvailable() {
        final var book = buildBook();

        when(bookStore.findByTitleAndAuthor(anyString(), anyString())).thenReturn(Optional.of(book));

        assertThrows(BadRequestException.class, () -> bookService.create(book));

        verify(bookStore).findByTitleAndAuthor(book.getTitle(), book.getAuthor());
    }

    @Test
    void shouldCreate_ThrownIsbn13BookAvailable() {
        final var book = buildBook();

        when(bookStore.findBookByIsbn13(anyString())).thenReturn(Optional.of(book));

        assertThrows(BadRequestException.class, () -> bookService.create(book));

        verify(bookStore).findBookByIsbn13(book.getIsbn13());
    }

    @Test
    void shouldUpdate_Admin_OK() {
        final var book = buildBook();
        final var bookUpdate = buildBook()
                .withId(book.getId())
                .withUserId(book.getUserId());

        when(bookStore.findById(book.getId())).thenReturn(Optional.of(book));
        when(bookStore.save(book)).thenReturn(book);
        when(authsProvider.getCurrentUserRole()).thenReturn(buildAdmin().getRole());

        final var actual = bookService.update(book.getId(), bookUpdate);

        assertEquals(bookUpdate.getId(), actual.getId());
        assertEquals(bookUpdate.getTitle(), actual.getTitle());
        assertEquals(bookUpdate.getAuthor(), actual.getAuthor());
        assertEquals(bookUpdate.getImage(), actual.getImage());
        assertEquals(bookUpdate.getDescription(), actual.getDescription());
        assertEquals(bookUpdate.getUserId(), actual.getUserId());

        verify(bookStore).findById(book.getId());
        verify(bookStore).save(book);
    }

    @Test
    void shouldUpdate_Contributor_OK() {
        final var book = buildBook();
        final var bookUpdate = buildBook()
                .withId(book.getId())
                .withUserId(book.getUserId());

        when(bookStore.findById(book.getId())).thenReturn(Optional.of(book));
        when(bookStore.save(book)).thenReturn(book);
        when(authsProvider.getCurrentUserRole()).thenReturn(buildContributor().getRole());
        when(authsProvider.getCurrentUserId()).thenReturn(buildContributor().getUserId());

        book.setUserId(authsProvider.getCurrentUserId());
        bookUpdate.setUserId(authsProvider.getCurrentUserId());

        final var actual = bookService.update(book.getId(), bookUpdate);

        assertEquals(bookUpdate.getId(), actual.getId());
        assertEquals(bookUpdate.getTitle(), actual.getTitle());
        assertEquals(bookUpdate.getAuthor(), actual.getAuthor());
        assertEquals(bookUpdate.getImage(), actual.getImage());
        assertEquals(bookUpdate.getDescription(), actual.getDescription());
        assertEquals(bookUpdate.getUserId(), actual.getUserId());

        verify(bookStore).findById(book.getId());
        verify(bookStore).save(book);
    }

    @Test
    void shouldUpdate_Contributor_ThrownForbiddenException() {
        final var book = buildBook();
        final var bookUpdate = buildBook();

        when(bookStore.findById(book.getId())).thenReturn(Optional.of(book));
        when(authsProvider.getCurrentUserRole()).thenReturn(buildContributor().getRole());
        when(authsProvider.getCurrentUserId()).thenReturn(buildContributor().getUserId());

        assertThrows(ForbiddenException.class, () -> bookService.update(book.getId(), bookUpdate));

        verify(bookStore).findById(book.getId());
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
    void shouldUpdate_ThrownIsbn13BookAvailable() {
        final var bookToUpdate = buildBook();
        final var bookExited = buildBook();
        final var bookUpdate = buildBook()
                .withIsbn13(bookExited.getIsbn13());

        when(bookStore.findById(bookToUpdate.getId())).thenReturn(Optional.of(bookToUpdate));
        when(bookStore.findBookByIsbn13(bookUpdate.getIsbn13())).thenReturn(Optional.of(bookUpdate));
        when(authsProvider.getCurrentUserRole()).thenReturn(buildAdmin().getRole());

        assertThrows(BadRequestException.class, () -> bookService.update(bookToUpdate.getId(), bookUpdate));

        verify(bookStore).findById(bookToUpdate.getId());
        verify(bookStore).findBookByIsbn13(bookUpdate.getIsbn13());
        verify(bookStore, never()).save(bookUpdate);
    }

    @Test
    void shouldDelete_Contributor_OK() {
        final var book = buildBook();

        when(bookStore.findById(book.getId())).thenReturn(Optional.of(book));
        when(authsProvider.getCurrentUserRole()).thenReturn(buildContributor().getRole());
        when(authsProvider.getCurrentUserId()).thenReturn(buildContributor().getUserId());

        book.setUserId(authsProvider.getCurrentUserId());
        bookService.delete(book.getId());

        verify(bookStore).findById(book.getId());
    }

    @Test
    void shouldDelete_Admin_OK() {
        final var book = buildBook();

        when(bookStore.findById(book.getId())).thenReturn(Optional.of(book));
        when(authsProvider.getCurrentUserRole()).thenReturn(buildAdmin().getRole());

        bookService.delete(book.getId());

        verify(bookStore).findById(book.getId());
    }

    @Test
    void shouldDelete_Contributor_ThrownForbiddenException() {
        final var book = buildBook();

        when(bookStore.findById(book.getId())).thenReturn(Optional.of(book));
        when(authsProvider.getCurrentUserRole()).thenReturn(buildContributor().getRole());
        when(authsProvider.getCurrentUserId()).thenReturn(buildContributor().getUserId());

        assertThrows(ForbiddenException.class, () -> bookService.delete(book.getId()));
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
    void shouldUploadImage_Contributor_OK() throws IOException {
        final var book = buildBook();
        final var bookUpdate = buildBook()
                .withId(book.getId());
        final var bytes = new byte[]{0x12, 0x34, 0x56, 0x78};

        when(bookStore.findById(book.getId())).thenReturn(Optional.of(book));
        when(authsProvider.getCurrentUserRole()).thenReturn(buildContributor().getRole());
        when(authsProvider.getCurrentUserId()).thenReturn(buildContributor().getUserId());

        book.setUserId(authsProvider.getCurrentUserId());
        bookUpdate.setUserId(authsProvider.getCurrentUserId());

        bookUpdate.setImage(cloudinaryService.upload(bytes));
        bookUpdate.setCreatedAt(Instant.now());
        bookService.uploadImage(bookUpdate.getId(), bytes);

        verify(bookStore).findById(book.getId());
    }

    @Test
    void shouldUploadImage_Admin_OK() throws IOException {
        final var book = buildBook();
        final var bookUpdate = buildBook()
                .withId(book.getId())
                .withUserId(book.getUserId());
        final var bytes = new byte[]{0x12, 0x34, 0x56, 0x78};

        when(bookStore.findById(book.getId())).thenReturn(Optional.of(book));
        when(authsProvider.getCurrentUserRole()).thenReturn(buildAdmin().getRole());

        bookUpdate.setImage(cloudinaryService.upload(bytes));
        bookUpdate.setCreatedAt(Instant.now());
        bookUpdate.setUserId(authsProvider.getCurrentUserId());
        bookService.uploadImage(bookUpdate.getId(), bytes);

        verify(bookStore).findById(book.getId());
        verify(authsProvider).getCurrentUserRole();
    }

    @Test
    void shouldUploadImage_Contributor_ThrownForbiddenException() {
        final var book = buildBook();
        final var bytes = new byte[]{0x12, 0x34, 0x56, 0x78};

        when(bookStore.findById(book.getId())).thenReturn(Optional.of(book));
        when(authsProvider.getCurrentUserRole()).thenReturn(buildContributor().getRole());
        when(authsProvider.getCurrentUserId()).thenReturn(buildContributor().getUserId());

        assertThrows(ForbiddenException.class, () -> bookService.uploadImage(book.getId(), bytes));

        verify(bookStore).findById(book.getId());
        verify(authsProvider).getCurrentUserRole();
        verify(authsProvider).getCurrentUserId();
        verify(bookStore, never()).save(book);
    }

    @Test
    void shouldUploadImage_ThrownNotFound() {
        final var book = buildBook();
        final var bytes = new byte[]{0x12, 0x34, 0x56, 0x78};

        when(bookStore.findById(book.getId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> bookService.uploadImage(book.getId(), bytes));

        verify(bookStore).findById(book.getId());
        verify(bookStore, never()).save(book);
    }
}