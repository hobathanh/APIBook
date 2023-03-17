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
}
