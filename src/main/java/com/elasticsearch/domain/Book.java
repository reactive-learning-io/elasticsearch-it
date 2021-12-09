package com.elasticsearch.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import lombok.Data;

@Document(indexName="book")
@Data
public class Book {
    
    @Id
    private String isbn;
    private String name;
    private String author;

}
