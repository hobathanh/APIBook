package com.bathanh.apibook.api.book;

import com.bathanh.apibook.domain.book.BookService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
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
        return toBookResponseDTOs(bookService.findAll());
    }

    @Operation(summary = "Find a specific book by id")
    @GetMapping("{id}")
    public BookResponseDTO findById(final @PathVariable UUID id) {
        return toBookResponseDTO(bookService.findById(id));
    }

    @Operation(summary = "Find books by title, author, description, subtitle, publisher, isbn13")
    @GetMapping("find")
    public List<BookResponseDTO> find(final @RequestParam String keyword) {
        return toBookResponseDTOs(bookService.find(keyword));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CONTRIBUTOR')")
    @Operation(summary = "Create a specific book")
    @PostMapping
    public BookResponseDTO create(final @RequestBody BookRequestDTO bookRequestDTO) {
        return toBookResponseDTO(bookService.create(toBook(bookRequestDTO)));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CONTRIBUTOR')")
    @Operation(summary = "Update a specific book")
    @PutMapping("{id}")
    public BookResponseDTO update(final @PathVariable UUID id, final @RequestBody BookRequestDTO bookRequestDTO) {
        return toBookResponseDTO(bookService.update(id, toBook(bookRequestDTO)));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CONTRIBUTOR')")
    @Operation(summary = "Delete a specific book")
    @DeleteMapping("{id}")
    public void delete(final @PathVariable UUID id) {
        bookService.delete(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CONTRIBUTOR')")
    @Operation(summary = "Upload Image a specific book")
    @PostMapping("{id}/image")
    public Optional<BookResponseDTO> upload(final @PathVariable UUID id, @RequestParam("file") final MultipartFile file) throws IOException {
        return Optional.ofNullable(toBookResponseDTO(bookService.uploadImage(id, file.getBytes())));
    }
}
