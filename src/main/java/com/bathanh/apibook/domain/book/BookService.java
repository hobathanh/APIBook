package com.bathanh.apibook.domain.book;

import com.bathanh.apibook.api.fetchbook.BookItemDTO;
import com.bathanh.apibook.api.fetchbook.BookItemDetailDTO;
import com.bathanh.apibook.api.fetchbook.BookMapper;
import com.bathanh.apibook.domain.auths.AuthsProvider;
import com.bathanh.apibook.domain.fetchbook.BookApiAdapter;
import com.bathanh.apibook.persistence.book.BookRepository;
import com.bathanh.apibook.persistence.book.BookStore;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
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
    private final BookApiAdapter bookApiAdapter;
    private final BookRepository bookRepository;

    @Value("${userIdCronJob}")
    private UUID userIdCronJob;

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

    private List<BookItemDTO> filterNewBooks(List<BookItemDTO> newBooks) {
        List<Book> existingBooks = bookStore.findAll();

        List<BookItemDTO> newBooksToInsert = new ArrayList<>();
        for (BookItemDTO book : newBooks) {
            if (existingBooks.stream().noneMatch(existingBook -> existingBook.getIsbn13().equals(book.getIsbn13()))) {
                newBooksToInsert.add(book);
            }
        }

        return newBooksToInsert;
    }

    public void storeNewBooks() {
        List<BookItemDTO> newBooks = bookApiAdapter.fetchNewBooks();
        List<BookItemDTO> booksToInsert = filterNewBooks(newBooks);

        for (BookItemDTO bookToInsert : booksToInsert) {
            BookItemDetailDTO bookDetail = bookApiAdapter.fetchBookDetail(bookToInsert.getIsbn13());
            Book book = BookMapper.toBookFromItemDetail(bookDetail);

            book.setUserId(userIdCronJob);
            book.setCreatedAt(Instant.now());

            verifyIsbn13BookAvailable(book);
            bookStore.create(book);
        }
    }

    public Book create(final Book book) {
        validate(book);
        verifyTitleAndAuthorAvailable(book.getTitle(), book.getAuthor());
        verifyIsbn13BookAvailable(book);

        book.setUserId(authsProvider.getCurrentUserId());
        book.setIsbn13(book.getIsbn13());
        book.setCreatedAt(Instant.now());

        return bookStore.create(book);
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

        return bookStore.update(updatedBook);
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

    private void verifyIsbn13BookAvailable(final Book book) {
        final Optional<Book> bookOptional = bookStore.findBookByIsbn13(book.getIsbn13());
        if (bookOptional.isPresent()) {
            throw supplyIsbn13BookAlreadyExist(book.getIsbn13()).get();
        }
    }
}
