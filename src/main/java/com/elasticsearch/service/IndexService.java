package com.elasticsearch.service;

import java.io.IOException;
import java.util.Collections;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.WriteRequest.RefreshPolicy;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class IndexService {

    @Autowired
    private RestHighLevelClient client;

    public IndexResponse createIndex() {

        IndexRequest indexRequest = new IndexRequest("spring-data").source(Collections.singletonMap("feature", "high-level-client")).setRefreshPolicy(RefreshPolicy.IMMEDIATE);

        IndexResponse response = null;
        try {
            response = client.index(indexRequest, RequestOptions.DEFAULT);
        } catch (IOException ex) {
            log.error("Error: {}", ex);
        }
        return response;
    }

}
