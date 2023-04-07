package com.bathanh.apibook.domain.book;

import com.bathanh.apibook.error.BadRequestException;

import static java.util.Calendar.YEAR;
import static java.util.Calendar.getInstance;
import static org.apache.commons.lang3.StringUtils.isBlank;

public class BookValidation {

    public static void validate(final Book book) {
        if (isBlank(book.getTitle())) {
            throw new BadRequestException("Title book is required, please check again");
        }

        if (isBlank(book.getAuthor())) {
            throw new BadRequestException("Author book is required, please check again");
        }

        if (isBlank(book.getSubtitle())) {
            throw new BadRequestException("Subtitle is required, please check again");
        }

        if (isBlank(book.getPublisher())) {
            throw new BadRequestException("Publisher is required, please check again");
        }

        if (isBlank(book.getIsbn13())) {
            throw new BadRequestException("Isbn13 is required, please check again");
        }

        if ((book.getIsbn13()).length() != 13) {
            throw new BadRequestException("Isbn13 is only contain 13 characters, please check again");
        }

        if (isBlank(book.getPrice())) {
            throw new BadRequestException("Price is required, please check again");
        }

        if (book.getYear() == null) {
            throw new BadRequestException("Year is required, please check again");
        }

        if (book.getYear() != getInstance().get(YEAR)) {
            throw new BadRequestException("Invalid year, please check again");
        }

        if (book.getRating() < 0 || book.getRating() > 5) {
            throw new BadRequestException("Rating cannot be outside the range from 0 to 5, please check again");
        }
    }
}
