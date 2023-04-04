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
        final var book = buildBook();
        book.setRating(3.5);
        book.setYear(2000);

        validate(book);
    }

    @Test
    void validate_ThrowTitleEmpty() {
        final var book = buildBook();
        book.setTitle(null);

        assertThrows(BadRequestException.class, () -> validate(book));
    }

    @Test
    void validate_ThrowAuthorEmpty() {
        final var book = buildBook();
        book.setAuthor(null);

        assertThrows(BadRequestException.class, () -> validate(book));
    }

    @Test
    void validate_ThrowUserIdEmpty() {
        final var book = buildBook();
        book.setUserId(null);

        assertThrows(BadRequestException.class, () -> validate(book));
    }

    @Test
    void validate_ThrowSubtitleEmpty() {
        final var book = buildBook();
        book.setSubtitle(null);

        assertThrows(BadRequestException.class, () -> validate(book));
    }

    @Test
    void validate_ThrowPublisherEmpty() {
        final var book = buildBook();
        book.setPublisher(null);

        assertThrows(BadRequestException.class, () -> validate(book));
    }

    @Test
    void validate_ThrowIsbn13Empty() {
        final var book = buildBook();
        book.setIsbn13(null);

        assertThrows(BadRequestException.class, () -> validate(book));
    }

    @Test
    void validate_ThrowInvalidIsbn13Length() {
        final var book = buildBook();
        book.setIsbn13(randomAlphabetic(5));

        assertThrows(BadRequestException.class, () -> validate(book));
    }

    @Test
    void validate_ThrowPriceEmpty() {
        final var book = buildBook();
        book.setPrice(null);

        assertThrows(BadRequestException.class, () -> validate(book));
    }

    @Test
    void validate_ThrowYearEmpty() {
        final var book = buildBook();
        book.setYear(null);

        assertThrows(BadRequestException.class, () -> validate(book));
    }

    @Test
    void validate_ThrowInvalidYear() {
        final var book = buildBook();
        book.setYear(2500);

        assertThrows(BadRequestException.class, () -> validate(book));
    }

    @Test
    void validate_ThrowInvalidRating() {
        final var book = buildBook();
        book.setRating(6.0);

        assertThrows(BadRequestException.class, () -> validate(book));
    }

}
