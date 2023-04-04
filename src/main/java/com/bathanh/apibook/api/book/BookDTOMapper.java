package com.bathanh.apibook.api.book;

import com.bathanh.apibook.domain.book.Book;
import lombok.experimental.UtilityClass;

import java.util.List;

import static org.apache.commons.collections4.ListUtils.emptyIfNull;

@UtilityClass
public class BookDTOMapper {

    public static BookResponseDTO toBookResponseDTO(final Book book) {
        return BookResponseDTO.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .description(book.getDescription())
                .createdAt(book.getCreatedAt())
                .updatedAt(book.getUpdatedAt())
                .image(book.getImage())
                .subtitle(book.getSubtitle())
                .publisher(book.getPublisher())
                .isbn13(book.getIsbn13())
                .price(book.getPrice())
                .year(book.getYear())
                .rating(book.getRating())
                .userId(book.getUserId())
                .build();
    }

    public static Book toBook(final BookRequestDTO bookRequestDTO) {
        return Book.builder()
                .title(bookRequestDTO.getTitle())
                .author(bookRequestDTO.getAuthor())
                .description(bookRequestDTO.getDescription())
                .image(bookRequestDTO.getImage())
                .subtitle(bookRequestDTO.getSubtitle())
                .publisher(bookRequestDTO.getPublisher())
                .isbn13(bookRequestDTO.getIsbn13())
                .price(bookRequestDTO.getPrice())
                .year(bookRequestDTO.getYear())
                .rating(bookRequestDTO.getRating())
                .build();
    }

    public static List<BookResponseDTO> toBookResponseDTOs(final List<Book> books) {
        return emptyIfNull(books)
                .stream()
                .map(BookDTOMapper::toBookResponseDTO)
                .toList();
    }
}
