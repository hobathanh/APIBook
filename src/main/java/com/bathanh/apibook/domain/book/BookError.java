package com.bathanh.apibook.domain.book;

import com.bathanh.apibook.error.BadRequestException;
import com.bathanh.apibook.error.NotFoundException;
import lombok.experimental.UtilityClass;

import java.util.UUID;
import java.util.function.Supplier;

@UtilityClass
public class BookError {

    public static Supplier<NotFoundException> supplyBookNotFound(final UUID id) {
        return () -> new NotFoundException("Book with id %s could not be found", id);
    }

    public static Supplier<BadRequestException> supplyBookAlreadyExist(final String title, final String author) {
        return () -> new BadRequestException("Book with title %s, author %s already exist", title, author);
    }

    public static Supplier<NotFoundException> supplyBookNotFoundIsbn13(final String isbn13) {
        return () -> new NotFoundException("Book with isbn13 %s could not be found", isbn13);
    }

    public static Supplier<BadRequestException> supplyIsbn13BookAlreadyExist(final String isbn13) {
        return () -> new BadRequestException("Book with isbn13 %s already exist", isbn13);
    }
}
