package com.bathanh.apibook.fakes;

import com.bathanh.apibook.api.book.BookItemDetailDTO;
import org.apache.commons.lang3.RandomStringUtils;

import java.security.SecureRandom;

public class FetchBookFakes {

    static final int year = new SecureRandom().nextInt(224) + 1800;
    static final double rating = new SecureRandom().nextDouble() * 5.0;

    public static BookItemDetailDTO buildBookItemDetailDTO() {
        return BookItemDetailDTO.builder()
                .title(RandomStringUtils.randomAlphabetic(3, 10))
                .authors(RandomStringUtils.randomAlphabetic(3, 10))
                .desc(RandomStringUtils.randomAlphabetic(3, 10))
                .image(RandomStringUtils.randomAlphabetic(3, 10))
                .subtitle(RandomStringUtils.randomAlphabetic(3, 10))
                .publisher(RandomStringUtils.randomAlphabetic(3, 10))
                .isbn13(RandomStringUtils.randomNumeric(13))
                .price(RandomStringUtils.randomAlphabetic(3, 10))
                .year(year)
                .rating(rating)
                .build();
    }
}
