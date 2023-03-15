package com.bathanh.apibook.fakes;

import com.bathanh.apibook.domain.book.Book;
import com.bathanh.apibook.persistence.book.BookEntity;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

@UtilityClass
public class BookFakes {

    public static Book buildBook() {
        return Book.builder()
                .id(UUID.randomUUID())
                .title(RandomStringUtils.randomAlphabetic(3, 10))
                .author(RandomStringUtils.randomAlphabetic(3, 10))
                .description(RandomStringUtils.randomAlphabetic(3, 10))
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .image(RandomStringUtils.randomAlphabetic(3, 10))
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
                .userId(UUID.randomUUID())
                .build();
    }

    public static List<BookEntity> buildBookEntities() {
        return IntStream.range(1, 5)
                .mapToObj(_ignored -> buildBookEntity())
                .toList();
    }
}
