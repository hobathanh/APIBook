package com.bathanh.apibook.domain.book;

import com.bathanh.apibook.error.BadRequestException;
import com.bathanh.apibook.error.NotFoundException;
import lombok.experimental.UtilityClass;

import java.util.function.Supplier;

@UtilityClass
public class BookError {

    public static Supplier<NotFoundException> supplyNotFound(final String fieldName, final String fieldValue) {
        return () -> new NotFoundException("Book with %s %s could not be found", fieldName, fieldValue);
    }

    public static Supplier<BadRequestException> supplyBookAlreadyExist(final String title, final String author) {
        return () -> new BadRequestException("Book with title %s, author %s already exist", title, author);
    }

    public static Supplier<BadRequestException> supplyIsbn13BookAlreadyExist(final String isbn13) {
        return () -> new BadRequestException("Book with isbn13 %s already exist", isbn13);
    }
}
