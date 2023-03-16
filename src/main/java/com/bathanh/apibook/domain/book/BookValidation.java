package com.bathanh.apibook.domain.book;

import com.bathanh.apibook.error.BadRequestException;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class BookValidation {

    public static void validate(final Book book) {
        if (isBlank(book.getTitle())) {
            throw new BadRequestException("Title book is required, please check again");
        }

        if (isBlank(book.getAuthor())) {
            throw new BadRequestException("Author book is required, please check again");
        }

        if (book.getUserId() == null) {
            throw new BadRequestException("UserId is required, please check again");
        }
    }
}
