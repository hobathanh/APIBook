package com.bathanh.apibook.persistence.book;

import com.bathanh.apibook.domain.book.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.bathanh.apibook.persistence.book.BookEntityMapper.*;
import static org.apache.commons.collections4.IterableUtils.toList;

@Repository
@RequiredArgsConstructor
public class BookStore {

    private final BookRepository bookRepository;

    public List<Book> findAll() {
        return toBooks(toList(bookRepository.findAll()));
    }

    public Optional<Book> findById(final UUID uuid) {
        return bookRepository.findById(uuid)
                .map(BookEntityMapper::toBook);
    }

    public Optional<Book> findBookByIsbn13(final String isbn13) {
        return bookRepository.findByIsbn13(isbn13)
                .map(BookEntityMapper::toBook);
    }

    public Optional<Book> findByTitleAndAuthor(final String title, final String author) {
        return bookRepository.findByTitleAndAuthor(title, author)
                .map(BookEntityMapper::toBook);
    }

    public List<Book> find(final String keyword) {
        return toBooks(bookRepository.find(keyword));
    }

    public Book save(final Book book) {
        return toBook(bookRepository.save(toBookEntity(book)));
    }

    public List<Book> saveAll(final List<Book> books) {
        return toBooks(toList(bookRepository.saveAll(toBookEntities(books))));
    }

    public void delete(final UUID id) {
        bookRepository.deleteById(id);
    }
}