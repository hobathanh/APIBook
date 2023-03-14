package com.bathanh.apibook.api.book;

import com.bathanh.apibook.domain.book.BookService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.bathanh.apibook.api.book.BookDTOMapper.*;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @Operation(summary = "Find all available books")
    @GetMapping
    public List<BookResponseDTO> findAll() {
        return toBookDTOs(bookService.findAll());
    }

    @Operation(summary = "Find a specific book by id")
    @GetMapping("{id}")
    public BookResponseDTO findById(final @PathVariable UUID id) {
        return toBookDTO(bookService.findById(id));
    }

    @Operation(summary = "Search books by keyword(title, author, description)")
    @GetMapping("search")
    public List<BookResponseDTO> search(final @RequestParam String keyword) {
        return toBookDTOs(bookService.search(keyword));
    }

    @Operation(summary = "Create a specific books")
    @PostMapping
    public BookResponseDTO create(final @RequestBody BookRequestDTO bookRequestDTO) {
        return toBookDTO(bookService.create(toBook(bookRequestDTO)));
    }

    @Operation(summary = "Update a specific book")
    @PutMapping("{id}")
    public BookResponseDTO update(final @PathVariable UUID id, final @RequestBody BookRequestDTO bookRequestDTO) {
        return toBookDTO(bookService.update(id, toBook(bookRequestDTO)));
    }

    @Operation(summary = "Delete a specific book")
    @DeleteMapping("{id}")
    public void delete(final @PathVariable UUID id) {
        bookService.delete(id);
    }
}
