package com.bathanh.apibook.persistence.book;

import com.bathanh.apibook.domain.book.Book;
import lombok.experimental.UtilityClass;

import java.util.List;

import static org.apache.commons.collections4.ListUtils.emptyIfNull;

@UtilityClass
public class BookEntityMapper {

    public static Book toBook(final BookEntity bookEntity) {
        return Book.builder()
                .id(bookEntity.getId())
                .title(bookEntity.getTitle())
                .author(bookEntity.getAuthor())
                .description(bookEntity.getDescription())
                .createdAt(bookEntity.getCreatedAt())
                .updatedAt(bookEntity.getUpdatedAt())
                .image(bookEntity.getImage())
                .userId(bookEntity.getUserId())
                .subtitle(bookEntity.getSubtitle())
                .publisher(bookEntity.getPublisher())
                .isbn13(bookEntity.getIsbn13())
                .price(bookEntity.getPrice())
                .year(bookEntity.getYear())
                .rating(bookEntity.getRating())
                .build();
    }

    public static BookEntity toBookEntity(final Book book) {
        return BookEntity.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .description(book.getDescription())
                .createdAt(book.getCreatedAt())
                .updatedAt(book.getUpdatedAt())
                .image(book.getImage())
                .userId(book.getUserId())
                .subtitle(book.getSubtitle())
                .publisher(book.getPublisher())
                .isbn13(book.getIsbn13())
                .price(book.getPrice())
                .year(book.getYear())
                .rating(book.getRating())
                .build();
    }

    public static List<Book> toBooks(final List<BookEntity> bookEntities) {
        return emptyIfNull(bookEntities)
                .stream()
                .map(BookEntityMapper::toBook)
                .toList();
    }

    public static List<BookEntity> toBookEntities(final List<Book> books) {
        return emptyIfNull(books)
                .stream()
                .map(BookEntityMapper::toBookEntity)
                .toList();
    }
}

