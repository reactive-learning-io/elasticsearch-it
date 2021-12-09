package com.elasticsearch.service;

import com.elasticsearch.domain.Book;
import com.elasticsearch.repository.BookRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public Page<Book> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    public List<Book> findByAuthor(String author) {
        return bookRepository.findByAuthor(author);
    }
    
    public Book save(Book book) {
        return bookRepository.save(book);
    }

}
