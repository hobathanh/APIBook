package com.bathanh.apibook.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.print.Book;
import java.util.List;

@RestController
public class BookController {
    @GetMapping("v1/books")
    List<Book> getBooks() {
        return null;
    }
}

