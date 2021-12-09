package com.elasticsearch.controller;

import com.elasticsearch.domain.Book;
import com.elasticsearch.service.BookService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/books")
    public Page<Book> getBooks(Pageable pageable) {
        return bookService.findAll(pageable);
    }

    @GetMapping("/books/{author}")
    public List<Book> getBooks(@PathVariable("author") String author) {
        return bookService.findByAuthor(author);
    }

    @PostMapping("/books")
    public Book addBook(@RequestBody Book book) {
        return bookService.save(book);
    }

}
