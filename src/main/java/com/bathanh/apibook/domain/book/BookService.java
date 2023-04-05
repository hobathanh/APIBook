package com.bathanh.apibook.domain.book;

import com.bathanh.apibook.domain.auths.AuthsProvider;
import com.bathanh.apibook.persistence.book.BookStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.bathanh.apibook.domain.book.BookError.*;
import static com.bathanh.apibook.domain.book.BookValidation.validate;
import static com.bathanh.apibook.error.CommonError.supplyForbiddenError;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookStore bookStore;
    private final AuthsProvider authsProvider;

    public List<Book> findAll() {
        return bookStore.findAll();
    }

    public Book findById(final UUID id) {
        return bookStore.findById(id).orElseThrow(supplyBookNotFound(id));
    }

    public List<Book> find(final String keyword) {
        return bookStore.find(keyword);
    }

    public Book findBookByIsbn13(final String isbn13) {
        return bookStore.findBookByIsbn13(isbn13)
                .orElseThrow(supplyBookNotFoundIsbn13(isbn13));
    }

    public Book create(final Book book) {
        validate(book);
        verifyTitleAndAuthorAvailable(book.getTitle(), book.getAuthor());
        verifyIsbn13BookAvailable(book);

        book.setUserId(authsProvider.getCurrentUserId());
        book.setIsbn13(book.getIsbn13());
        book.setCreatedAt(Instant.now());

        return bookStore.save(book);
    }

    public Book update(final UUID id, final Book book) {
        validate(book);
        verifyTitleAndAuthorAvailable(book.getTitle(), book.getAuthor());
        verifyIsbn13BookAvailable(book);

        final Book updatedBook = findById(id);

        verifyUpdateBookPermission(updatedBook);

        updatedBook.setTitle(book.getTitle());
        updatedBook.setAuthor(book.getAuthor());
        updatedBook.setDescription(book.getDescription());
        updatedBook.setImage(book.getImage());
        updatedBook.setSubtitle(book.getSubtitle());
        updatedBook.setPublisher(book.getPublisher());
        updatedBook.setIsbn13(book.getIsbn13());
        updatedBook.setPrice(book.getPrice());
        updatedBook.setYear(book.getYear());
        updatedBook.setRating(book.getRating());
        updatedBook.setUpdatedAt(Instant.now());

        return bookStore.save(updatedBook);
    }

    public void delete(final UUID id) {
        final Book book = findById(id);
        verifyDeleteBookPermission(book);

        bookStore.delete(id);
    }

    private void verifyTitleAndAuthorAvailable(final String title, final String author) {
        final Optional<Book> bookOptional = bookStore.findByTitleAndAuthor(title, author);
        if (bookOptional.isPresent()) {
            throw supplyBookAlreadyExist(title, author).get();
        }
    }

    private void verifyUpdateBookPermission(final Book book) {
        if (authsProvider.getCurrentUserRole().equals("ROLE_CONTRIBUTOR")
                && !authsProvider.getCurrentUserId().equals(book.getUserId())) {
            throw supplyForbiddenError().get();
        }
    }

    private void verifyDeleteBookPermission(final Book book) {
        if (authsProvider.getCurrentUserRole().equals("ROLE_CONTRIBUTOR")
                && !authsProvider.getCurrentUserId().equals(book.getUserId())) {
            throw supplyForbiddenError().get();
        }
    }

    public void verifyIsbn13BookAvailable(final Book book) {
        final Optional<Book> bookOptional = bookStore.findBookByIsbn13(book.getIsbn13());
        if (bookOptional.isPresent()) {
            throw supplyIsbn13BookAlreadyExist(book.getIsbn13()).get();
        }
    }
}
