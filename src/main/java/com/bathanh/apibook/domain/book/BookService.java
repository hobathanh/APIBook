package com.bathanh.apibook.domain.book;

import com.bathanh.apibook.persistence.book.BookStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static com.bathanh.apibook.domain.book.BookError.supplyBookNotFound;
import static com.bathanh.apibook.domain.book.BookValidation.validate;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookStore bookStore;

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

        book.setCreatedAt(Instant.now());
        return bookStore.create(book);
    }

    public Book update(final UUID id, final Book book) {
        validate(book);
        final Book updatedBook = findById(id);

        updatedBook.setTitle(book.getTitle());
        updatedBook.setAuthor(book.getAuthor());
        updatedBook.setDescription(book.getDescription());
        updatedBook.setImage(book.getImage());
        updatedBook.setUpdatedAt(Instant.now());

        return bookStore.update(updatedBook);
    }

    public void delete(final UUID id) {
        findById(id);
        bookStore.delete(id);
    }
}
