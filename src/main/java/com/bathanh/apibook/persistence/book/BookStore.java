package com.bathanh.apibook.persistence.book;

import com.bathanh.apibook.domain.book.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.apache.commons.collections4.IterableUtils.toList;

@Repository
@RequiredArgsConstructor
public class BookStore {

    private final BookRepository bookRepository;

    public List<Book> findAll() {
        return BookEntityMapper.toBooks(toList(bookRepository.findAll()));
    }

    public Optional<Book> findById(final UUID uuid) {
        return bookRepository.findById(uuid)
                .map(BookEntityMapper::toBook);
    }

    public List<Book> search(final String keyword) {
        return BookEntityMapper.toBooks(bookRepository.findAllByTitleOrAuthorOrDescription(keyword));
    }

    public Book create(final Book book) {
        return BookEntityMapper.toBook(bookRepository.save(BookEntityMapper.toBookEntity(book)));
    }

    public Book update(final Book book) {
        return BookEntityMapper.toBook(bookRepository.save(BookEntityMapper.toBookEntity(book)));
    }

    public void delete(final UUID id) {
        bookRepository.deleteById(id);
    }
}