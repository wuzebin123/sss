package com.baizhi.service;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class PoemServiceImpl {
    public List<Object> findAll(String s) throws ExecutionException, InterruptedException, UnknownHostException {
        TransportAddress transportAddress = new TransportAddress(InetAddress.getByName("192.168.211.33"), 9300);
        TransportClient transportClient = new PreBuiltTransportClient(Settings.EMPTY).addTransportAddress(transportAddress);
        QueryStringQueryBuilder queryStringQueryBuilder = QueryBuilders.queryStringQuery(s)
                .analyzer("ik_max_word")
                .field("id")
                .field("name")
                .field("author")
                .field("type")
                .field("content")
                .field("href")
                .field("authordes")
                .field("origin")
                .field("categoryid");
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder
                .field("id")
                .field("name")
                .field("author")
                .field("type")
                .field("content")
                .field("href")
                .field("authordes")
                .field("origin")
                .field("categoryid")
                .requireFieldMatch(false)
                .preTags("<font color=\"red\">")
                .postTags("</font>");

        SearchResponse searchResponse = transportClient.prepareSearch("poems").setTypes("poem").setQuery(queryStringQueryBuilder).highlighter(highlightBuilder).setSize(100).execute().get();

        SearchHit[] hits1 = searchResponse.getHits().getHits();
        ArrayList<Object> list = new ArrayList<>();
        for (SearchHit documentFields : hits1) {
            Map<String, HighlightField> highlightFields = documentFields.getHighlightFields();
            Map<String, Object> sourceAsMap = documentFields.getSourceAsMap();
            Map<String, Object> map = new HashMap<>();
            ArrayList<String> list1 = new ArrayList<>();
            list1.add("id");
            list1.add("name");
            list1.add("author");
            list1.add("type");
            list1.add("content");
            list1.add("href");
            list1.add("authordes");
            list1.add("origin");
            list1.add("categoryid");
            for (String s1 : list1) {
                if (highlightFields.get(s1) == null) {
                    map.put(s1, sourceAsMap.get(s1));
                } else {
                    map.put(s1, highlightFields.get(s1).getFragments()[0].toString());
                }
            }
            list.add(map);
        }
        return list;


    }
}
