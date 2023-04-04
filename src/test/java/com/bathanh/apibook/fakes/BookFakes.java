package com.bathanh.apibook.fakes;

import com.bathanh.apibook.domain.book.Book;
import com.bathanh.apibook.persistence.book.BookEntity;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.RandomStringUtils;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

@UtilityClass
public class BookFakes {

    static final int year = new SecureRandom().nextInt(224) + 1800;
    static final double rating = new SecureRandom().nextDouble() * 5.0;

    public static Book buildBook() {
        return Book.builder()
                .id(UUID.randomUUID())
                .title(RandomStringUtils.randomAlphabetic(3, 10))
                .author(RandomStringUtils.randomAlphabetic(3, 10))
                .description(RandomStringUtils.randomAlphabetic(3, 10))
                .createdAt(Instant.parse("2023-03-15T11:05:00.123456Z"))
                .updatedAt(Instant.parse("2023-03-15T11:06:00.123456Z"))
                .image(RandomStringUtils.randomAlphabetic(3, 10))
                .subtitle(RandomStringUtils.randomAlphabetic(3, 10))
                .publisher(RandomStringUtils.randomAlphabetic(3, 10))
                .isbn13(RandomStringUtils.randomNumeric(13))
                .price(RandomStringUtils.randomAlphabetic(3, 10))
                .year(year)
                .rating(rating)
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
                .title(RandomStringUtils.randomAlphabetic(3, 10))
                .author(RandomStringUtils.randomAlphabetic(3, 10))
                .description(RandomStringUtils.randomAlphabetic(3, 10))
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .image(RandomStringUtils.randomAlphabetic(3, 10))
                .subtitle(RandomStringUtils.randomAlphabetic(3, 10))
                .publisher(RandomStringUtils.randomAlphabetic(3, 10))
                .isbn13(RandomStringUtils.randomNumeric(13))
                .price(RandomStringUtils.randomAlphabetic(3, 10))
                .year(year)
                .rating(rating)
                .userId(UUID.randomUUID())
                .build();
    }

    public static List<BookEntity> buildBookEntities() {
        return IntStream.range(1, 5)
                .mapToObj(_ignored -> buildBookEntity())
                .toList();
    }
}
