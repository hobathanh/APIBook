package com.bathanh.apibook.domain.book;

import com.bathanh.apibook.domain.auths.AuthsProvider;
import com.bathanh.apibook.domain.auths.UserAuthenticationToken;
import com.bathanh.apibook.persistence.book.BookStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.bathanh.apibook.domain.book.BookError.supplyBookAlreadyExist;
import static com.bathanh.apibook.domain.book.BookError.supplyBookNotFound;
import static com.bathanh.apibook.domain.book.BookValidation.validate;
import static com.bathanh.apibook.error.CommonError.supplyForbiddenError;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookStore bookStore;

    private final AuthsProvider authsProvider;

    private UserAuthenticationToken getCurrentAuthUser() {
        return authsProvider.getCurrentAuthentication();
    }

    public List<Book> findAll() {
        return bookStore.findAll();
    }

    public Book findById(final UUID id) {
        return bookStore.findById(id)
                .orElseThrow(supplyBookNotFound(id));
    }

    public List<Book> find(final String keyword) {
        return bookStore.find(keyword);
    }

    public Book create(final Book book) {
        validate(book);
        verifyTitleAndAuthorAvailable(book.getTitle(), book.getAuthor());

        book.setUserId(getCurrentAuthUser().getUserId());
        book.setCreatedAt(Instant.now());
        return bookStore.create(book);
    }

    public Book update(final UUID id, final Book book) {
        validate(book);
        final Book updatedBook = findById(id);

        verifyUpdateBookPermission(updatedBook.getUserId());

        updatedBook.setTitle(book.getTitle());
        updatedBook.setAuthor(book.getAuthor());
        updatedBook.setDescription(book.getDescription());
        updatedBook.setImage(book.getImage());
        updatedBook.setUpdatedAt(Instant.now());

        return bookStore.update(updatedBook);
    }

    public void delete(final UUID id) {
        final Book book = findById(id);
        verifyDeleteBookPermission(book.getUserId());

        bookStore.delete(id);
    }

    private void verifyTitleAndAuthorAvailable(final String title, final String author) {
        final Optional<Book> bookOptional = bookStore.findByTitleAndAuthor(title, author);
        if (bookOptional.isPresent()) {
            throw supplyBookAlreadyExist(title, author).get();
        }
    }

    private void verifyUpdateBookPermission(UUID id) {
        if (getCurrentAuthUser().getRole().equals("ROLE_CONTRIBUTOR")
                && !getCurrentAuthUser().getUserId().equals(id)) {
            throw supplyForbiddenError("You do not have permission to update book").get();
        }
    }

    private void verifyDeleteBookPermission(UUID id) {
        if (getCurrentAuthUser().getRole().equals("ROLE_CONTRIBUTOR")
                && !getCurrentAuthUser().getUserId().equals(id)) {
            throw supplyForbiddenError("You do not have permission to delete book").get();
        }
    }
}
