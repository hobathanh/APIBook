package com.bathanh.apibook.integration;

import com.bathanh.apibook.api.book.BookItemDTO;
import com.bathanh.apibook.api.book.BookItemDetailDTO;
import com.bathanh.apibook.domain.book.Book;
import com.bathanh.apibook.persistence.book.BookStore;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static com.bathanh.apibook.api.book.BookMapper.toBookFromItemDetail;

@Service
@RequiredArgsConstructor
public class ItBookService {

    private final BookApiAdapter bookApiAdapter;
    private final BookStore bookStore;
    @Value("${user-id-cron-job}")
    private UUID userIdCronJob;

    public void storeNewBooks() {
        final List<BookItemDTO> newBooks = bookApiAdapter.fetchNewBooks();
        final List<BookItemDTO> booksToInsert = filterNewBooks(newBooks);

        final List<Book> bookList = booksToInsert.stream()
                .map(bookToInsert -> {
                    final BookItemDetailDTO bookDetail = bookApiAdapter.fetchBookDetail(bookToInsert.getIsbn13());
                    final Book book = toBookFromItemDetail(bookDetail);

                    book.setUserId(userIdCronJob);
                    book.setCreatedAt(Instant.now());

                    return book;
                })
                .toList();

        bookStore.saveAll(bookList);
    }

    private List<BookItemDTO> filterNewBooks(final List<BookItemDTO> newBooks) {
        final List<Book> existingBooks = bookStore.findAll();

        return newBooks.stream()
                .filter(book -> existingBooks.stream()
                        .noneMatch(existingBook -> existingBook.getIsbn13().equals(book.getIsbn13())))
                .toList();
    }
}
