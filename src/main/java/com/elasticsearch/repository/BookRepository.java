package com.elasticsearch.repository;

import com.elasticsearch.domain.Book;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends ElasticsearchRepository<Book, String> {
    
    public List<Book> findByAuthor(String author);

}
