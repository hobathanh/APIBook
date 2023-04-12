package com.bathanh.apibook.domain.book;

import com.bathanh.apibook.domain.auths.AuthsProvider;
import com.bathanh.apibook.persistence.book.BookStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
    private final CloudinaryService cloudinaryService;

    public List<Book> findAll() {
        return bookStore.findAll();
    }

    public Book findById(final UUID id) {
        return bookStore.findById(id).orElseThrow(supplyNotFound("id", String.valueOf(id)));
    }

    public List<Book> find(final String keyword) {
        return bookStore.find(keyword);
    }

    public Book findBookByIsbn13(final String isbn13) {
        return bookStore.findBookByIsbn13(isbn13)
                .orElseThrow(supplyNotFound("isbn13", isbn13));
    }

    public Book create(final Book book) {
        validate(book);
        verifyTitleAndAuthorAvailable(book.getTitle(), book.getAuthor());
        verifyIsbn13BookAvailable(book.getIsbn13());

        final double bookRating = book.getRating() == null ? 0.0 : book.getRating();

        final Book bookToCreate = book
                .withUserId(authsProvider.getCurrentUserId())
                .withIsbn13(book.getIsbn13())
                .withCreatedAt(Instant.now())
                .withRating(bookRating);

        return bookStore.save(bookToCreate);
    }

    public Book update(final UUID id, final Book book) {
        final Book bookToUpdate = findById(id);
        validate(book);
        verifyUpdateBookPermission(bookToUpdate);

        if (!bookToUpdate.getIsbn13().equals(book.getIsbn13())) {
            verifyIsbn13BookAvailable(book.getIsbn13());
            bookToUpdate.setIsbn13(book.getIsbn13());
        }

        bookToUpdate.setTitle(book.getTitle());
        bookToUpdate.setAuthor(book.getAuthor());
        bookToUpdate.setDescription(book.getDescription());
        bookToUpdate.setImage(book.getImage());
        bookToUpdate.setSubtitle(book.getSubtitle());
        bookToUpdate.setPublisher(book.getPublisher());
        bookToUpdate.setPrice(book.getPrice());
        bookToUpdate.setYear(book.getYear());
        bookToUpdate.setRating(book.getRating());
        bookToUpdate.setUpdatedAt(Instant.now());

        return bookStore.save(bookToUpdate);
    }

    public void delete(final UUID id) {
        final Book book = findById(id);
        verifyDeleteBookPermission(book);

        bookStore.delete(id);
    }

    public Book uploadImage(final UUID id, final byte[] image) throws IOException {
        final Book book = findById(id);
        verifyUpdateBookPermission(book);

        book.setImage(cloudinaryService.upload(image));
        book.setUpdatedAt(Instant.now());

        return bookStore.save(book);
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

    private void verifyIsbn13BookAvailable(final String isbn13) {
        bookStore.findBookByIsbn13(isbn13)
                .ifPresent(b -> {
                    throw supplyIsbn13BookAlreadyExist(isbn13).get();
                });
    }
}
