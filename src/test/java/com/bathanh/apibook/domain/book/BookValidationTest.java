package com.bathanh.apibook.domain.book;

import com.bathanh.apibook.error.BadRequestException;
import org.junit.jupiter.api.Test;

import static com.bathanh.apibook.domain.book.BookValidation.validate;
import static com.bathanh.apibook.fakes.BookFakes.buildBook;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BookValidationTest {

    @Test
    void validate_OK() {
        final var book = buildBook()
                .withRating(3.5)
                .withYear(2001);

        validate(book);
    }

    @Test
    void validate_ThrowTitleEmpty() {
        final var book = buildBook()
                .withTitle(null);

        assertThrows(BadRequestException.class, () -> validate(book));
    }

    @Test
    void validate_ThrowAuthorEmpty() {
        final var book = buildBook()
                .withAuthor(null);

        assertThrows(BadRequestException.class, () -> validate(book));
    }

    @Test
    void validate_ThrowSubtitleEmpty() {
        final var book = buildBook()
                .withSubtitle(null);

        assertThrows(BadRequestException.class, () -> validate(book));
    }

    @Test
    void validate_ThrowPublisherEmpty() {
        final var book = buildBook()
                .withPublisher(null);

        assertThrows(BadRequestException.class, () -> validate(book));
    }

    @Test
    void validate_ThrowIsbn13Empty() {
        final var book = buildBook()
                .withIsbn13(null);

        assertThrows(BadRequestException.class, () -> validate(book));
    }

    @Test
    void validate_ThrowInvalidIsbn13Length() {
        final var book = buildBook()
                .withIsbn13(randomAlphabetic(5));

        assertThrows(BadRequestException.class, () -> validate(book));
    }

    @Test
    void validate_ThrowPriceEmpty() {
        final var book = buildBook()
                .withPrice(null);

        assertThrows(BadRequestException.class, () -> validate(book));
    }

    @Test
    void validate_ThrowYearEmpty() {
        final var book = buildBook()
                .withYear(null);

        assertThrows(BadRequestException.class, () -> validate(book));
    }

    @Test
    void validate_ThrowInvalidYear() {
        final var book = buildBook()
                .withYear(2024);

        assertThrows(BadRequestException.class, () -> validate(book));
    }

    @Test
    void validate_ThrowInvalidRating() {
        final var book = buildBook()
                .withRating(6.0);

        assertThrows(BadRequestException.class, () -> validate(book));
    }

}
