package com.elasticsearch.controller;

import com.elasticsearch.service.IndexService;

import org.elasticsearch.action.index.IndexResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @Autowired
    private IndexService indexService;

    @GetMapping(value = "/index")
    public IndexResponse getIndex() {
        return indexService.createIndex();
    }

}
