package com.bathanh.apibook.domain.book;

import com.bathanh.apibook.error.BadRequestException;
import com.bathanh.apibook.persistence.book.BookStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static com.bathanh.apibook.domain.book.BookError.supplyBookNotFound;
import static org.apache.commons.lang3.StringUtils.isBlank;

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

    public List<Book> search(final String keyword) {
        return bookStore.search(keyword);
    }

    public Book create(final Book book) {
        validatePropertyIsEmpty(book);

        book.setCreatedAt(Instant.now());
        return bookStore.create(book);
    }

    public Book update(final UUID id, final Book book) {
        validatePropertyIsEmpty(book);
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

    private void validatePropertyIsEmpty(final Book book) {
        if (isBlank(book.getTitle()) || book.getTitle() == null) {
            throw new BadRequestException("Title book is required, please check again");
        }
        if (isBlank(book.getAuthor()) || book.getAuthor() == null) {
            throw new BadRequestException("Author book is required, please check again");
        }
        if (book.getUserId() == null) {
            throw new BadRequestException("UserId is required, please check again");
        }
    }
}
