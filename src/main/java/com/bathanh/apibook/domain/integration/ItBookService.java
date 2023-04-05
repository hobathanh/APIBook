package com.bathanh.apibook.domain.integration;

import com.bathanh.apibook.api.book.BookItemDTO;
import com.bathanh.apibook.api.book.BookItemDetailDTO;
import com.bathanh.apibook.domain.book.Book;
import com.bathanh.apibook.domain.book.BookService;
import com.bathanh.apibook.persistence.book.BookStore;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.bathanh.apibook.api.book.BookMapper.toBookFromItemDetail;

@Service
@RequiredArgsConstructor
public class ItBookService {

    private final BookApiAdapter bookApiAdapter;
    private final BookStore bookStore;
    private final BookService bookService;
    @Value("${userIdCronJob}")
    private UUID userIdCronJob;

    private List<BookItemDTO> filterNewBooks(List<BookItemDTO> newBooks) {
        List<Book> existingBooks = bookStore.findAll();

        List<BookItemDTO> newBooksToInsert = newBooks.stream()
                .filter(book -> existingBooks.stream()
                        .noneMatch(existingBook -> existingBook.getIsbn13().equals(book.getIsbn13())))
                .collect(Collectors.toList());

        return newBooksToInsert;
    }

    public void storeNewBooks() {
        List<BookItemDTO> newBooks = bookApiAdapter.fetchNewBooks();
        List<BookItemDTO> booksToInsert = filterNewBooks(newBooks);

        List<Book> bookList = new ArrayList<>();

        for (BookItemDTO bookToInsert : booksToInsert) {
            final BookItemDetailDTO bookDetail = bookApiAdapter.fetchBookDetail(bookToInsert.getIsbn13());
            Book book = toBookFromItemDetail(bookDetail);

            book.setUserId(userIdCronJob);
            book.setCreatedAt(Instant.now());

            bookService.verifyIsbn13BookAvailable(book);

            bookList.add(book);
        }

        bookStore.saveAll(bookList);
    }
}
