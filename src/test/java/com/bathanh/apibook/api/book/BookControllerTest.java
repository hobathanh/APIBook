package com.bathanh.apibook.api.book;

import com.bathanh.apibook.domain.book.Book;
import com.bathanh.apibook.domain.book.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static com.bathanh.apibook.api.book.BookDTOMapper.toBookResponseDTO;
import static com.bathanh.apibook.fakes.BookFakes.buildBook;
import static com.bathanh.apibook.fakes.BookFakes.buildBooks;
import static java.util.UUID.randomUUID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
@AutoConfigureMockMvc
class BookControllerTest extends AbstractControllerTest {

    private static final String BASE_URL = "/api/v1/books";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;

    final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Test
    void shouldFindAll_OK() throws Exception {
        final var books = buildBooks();

        when(bookService.findAll()).thenReturn(books);

        getRequest(mvc, BASE_URL)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(books.size()))
                .andExpect(jsonPath("$[0].id").value(books.get(0).getId().toString()))
                .andExpect(jsonPath("$[0].title").value(books.get(0).getTitle()))
                .andExpect(jsonPath("$[0].author").value(books.get(0).getAuthor()))
                .andExpect(jsonPath("$[0].description").value(books.get(0).getDescription()))
                .andExpect(jsonPath("$[0].createdAt").value(books.get(0).getCreatedAt().toString()))
                .andExpect(jsonPath("$[0].updatedAt").value(books.get(0).getUpdatedAt().toString()))
                .andExpect(jsonPath("$[0].image").value(books.get(0).getImage()))
                .andExpect(jsonPath("$[0].userId").value(books.get(0).getUserId().toString()));

        verify(bookService).findAll();
    }

    @Test
    void shouldFindById_OK() throws Exception {
        final var book = buildBook();

        when(bookService.findById(book.getId())).thenReturn(book);

        getRequest(mvc, BASE_URL + "/" + book.getId())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(book.getId().toString()))
                .andExpect(jsonPath("$.title").value(book.getTitle()))
                .andExpect(jsonPath("$.author").value(book.getAuthor()))
                .andExpect(jsonPath("$.description").value(book.getDescription()))
                .andExpect(jsonPath("$.createdAt").value(book.getCreatedAt().toString()))
                .andExpect(jsonPath("$.updatedAt").value(book.getUpdatedAt().toString()))
                .andExpect(jsonPath("$.image").value(book.getImage()))
                .andExpect(jsonPath("$.userId").value(book.getUserId().toString()));

        verify(bookService).findById(book.getId());
    }

    @Test
    void shouldFind_OK() throws Exception {
        final var book = buildBook();
        final var expected = buildBooks();

        when(bookService.find(book.getTitle())).thenReturn(expected);

        getRequest(mvc, BASE_URL + "/find?keyword=" + book.getTitle())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(expected.size()))
                .andExpect(jsonPath("$[0].id").value(expected.get(0).getId().toString()))
                .andExpect(jsonPath("$[0].title").value(expected.get(0).getTitle()))
                .andExpect(jsonPath("$[0].author").value(expected.get(0).getAuthor()))
                .andExpect(jsonPath("$[0].description").value(expected.get(0).getDescription()))
                .andExpect(jsonPath("$[0].createdAt").value(expected.get(0).getCreatedAt().toString()))
                .andExpect(jsonPath("$[0].updatedAt").value(expected.get(0).getUpdatedAt().toString()))
                .andExpect(jsonPath("$[0].image").value(expected.get(0).getImage()))
                .andExpect(jsonPath("$[0].userId").value(expected.get(0).getUserId().toString()));

        verify(bookService).find(book.getTitle());
    }

    @Test
    void shouldCreate_OK() throws Exception {
        final var book = buildBook();

        when(bookService.create(any(Book.class))).thenReturn(book);

        final String requestBody = mapper.writeValueAsString(book);

        postRequest(mvc, BASE_URL, requestBody)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(book.getTitle()))
                .andExpect(jsonPath("$.author").value(book.getAuthor()))
                .andExpect(jsonPath("$.description").value(book.getDescription()))
                .andExpect(jsonPath("$.createdAt").value(book.getCreatedAt().toString()))
                .andExpect(jsonPath("$.image").value(book.getImage()))
                .andExpect(jsonPath("$.userId").value(book.getUserId().toString()));

        verify(bookService).create(any(Book.class));
    }

    @Test
    void shouldUpdate_OK() throws Exception {
        final var book = buildBook();
        final var updatedBook = buildBook().withId(book.getId());

        when(bookService.update(any(UUID.class), any(Book.class))).thenReturn(updatedBook);

        final String requestBody = mapper.writeValueAsString(toBookResponseDTO(updatedBook));

        putRequest(mvc, BASE_URL + "/" + book.getId(), requestBody)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(updatedBook.getId().toString()))
                .andExpect(jsonPath("$.title").value(updatedBook.getTitle()))
                .andExpect(jsonPath("$.author").value(updatedBook.getAuthor()))
                .andExpect(jsonPath("$.description").value(updatedBook.getDescription()))
                .andExpect(jsonPath("$.updatedAt").value(updatedBook.getUpdatedAt().toString()))
                .andExpect(jsonPath("$.image").value(updatedBook.getImage()))
                .andExpect(jsonPath("$.userId").value(updatedBook.getUserId().toString()));

        verify(bookService).update(any(UUID.class), any(Book.class));
    }

    @Test
    void shouldDelete_OK() throws Exception {
        final var id = randomUUID();

        doNothing().when(bookService).delete(id);

        deleteRequest(mvc, BASE_URL + "/" + id)
                .andExpect(status().isOk());

        verify(bookService).delete(id);
    }
}