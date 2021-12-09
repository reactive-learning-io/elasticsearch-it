package com.elasticsearch;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.elasticsearch.ElasticsearchStatusException;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.MainResponse;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testcontainers.elasticsearch.ElasticsearchContainer;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * Elastic Search Integration test class.
 * <p>
 * <b>Note: You should have docker installed on your machine.</b>
 * </p>
 * <p>
 * It will start the Elasticsearch container in docker.
 * </p>
 *
 * @author Anil Jaglan
 * @version 1.0
 */
@Slf4j
public class EsIntegrationTest {

    private static ElasticsearchContainer container;

    private static RestHighLevelClient client;

    private static final String INDEX = "movie_idx";

    @BeforeClass
    public static void setup() throws IOException {
        container = new ElasticsearchContainer("docker.elastic.co/elasticsearch/elasticsearch:7.7.1");
        container.start();
        RestClientBuilder builder = RestClient.builder(HttpHost.create(container.getHttpHostAddress()));
        client = new RestHighLevelClient(builder);

        // Make sure the cluster is running
        MainResponse info = client.info(RequestOptions.DEFAULT);
        log.info("Client is running against an elasticsearch cluster {}.", info.getVersion().toString());
    }

    @Test
    public void testRemoveAndCreateIndexScenario() throws IOException {
        // Removing any existing index
        try {
            log.info("-> Removing index {}.", INDEX);
            client.indices().delete(new DeleteIndexRequest(INDEX), RequestOptions.DEFAULT);
        } catch (ElasticsearchStatusException e) {
            assertThat(e.status().getStatus(), is(404));
        }

        // Creating a new index
        log.info("-> Creating index {}.", INDEX);
        client.indices().create(new CreateIndexRequest(INDEX), RequestOptions.DEFAULT);

        // Indexing some documents
        log.info("-> Indexing one document in {}.", INDEX);
        IndexResponse ir = client.index(
                new IndexRequest(INDEX).source(jsonBuilder().startObject().field("name", "Twilight Saga").endObject()).setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE),
                RequestOptions.DEFAULT);
        log.info("-> Document indexed with _id {}.", ir.getId());

        // Searching
        SearchResponse sr = client.search(new SearchRequest(INDEX), RequestOptions.DEFAULT);
        log.info("{}", sr);
        assertThat(sr.getHits().getTotalHits().value, is(1L));
    }

    @AfterClass
    public static void teardown() throws IOException {
        try {
            if (client != null) {
                log.info("Closing elasticsearch client.");
                client.close();
            }
        } finally {
            container.stop();
        }
    }

}
