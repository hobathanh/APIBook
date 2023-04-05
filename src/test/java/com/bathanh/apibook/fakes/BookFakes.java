package com.bathanh.apibook.fakes;

import com.bathanh.apibook.domain.book.Book;
import com.bathanh.apibook.persistence.book.BookEntity;
import lombok.experimental.UtilityClass;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import static java.time.Instant.parse;
import static java.time.Year.now;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;

@UtilityClass
public class BookFakes {

    public static Book buildBook() {
        return Book.builder()
                .id(UUID.randomUUID())
                .title(randomAlphabetic(3, 10))
                .author(randomAlphabetic(3, 10))
                .description(randomAlphabetic(3, 10))
                .createdAt(parse("2023-03-15T11:05:00.123456Z"))
                .updatedAt(parse("2023-03-15T11:06:00.123456Z"))
                .image(randomAlphabetic(3, 10))
                .subtitle(randomAlphabetic(3, 10))
                .publisher(randomAlphabetic(3, 10))
                .isbn13(randomNumeric(13))
                .price(randomAlphabetic(3, 10))
                .year(generateYear())
                .rating(generateRating())
                .userId(UUID.randomUUID())
                .build();
    }

    public static List<Book> buildBooks() {
        return IntStream.range(1, 5)
                .mapToObj(_ignored -> buildBook())
                .toList();
    }

    public static BookEntity buildBookEntity() {
        return BookEntity.builder()
                .id(UUID.randomUUID())
                .title(randomAlphabetic(3, 10))
                .author(randomAlphabetic(3, 10))
                .description(randomAlphabetic(3, 10))
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .image(randomAlphabetic(3, 10))
                .subtitle(randomAlphabetic(3, 10))
                .publisher(randomAlphabetic(3, 10))
                .isbn13(randomNumeric(13))
                .price(randomAlphabetic(3, 10))
                .year(2023)
                .rating(generateRating())
                .userId(UUID.randomUUID())
                .build();
    }

    public static List<BookEntity> buildBookEntities() {
        return IntStream.range(1, 5)
                .mapToObj(_ignored -> buildBookEntity())
                .toList();
    }

    public static int generateYear() {
        return now().getValue();
    }

    public static double generateRating() {
        return new SecureRandom().nextDouble() * 5.0;
    }
}
