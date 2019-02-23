package com.zyw.spider.activiy;

import com.zyw.spider.model.NewsWithRelated;
import com.zyw.spider.model.SearchSate;
import com.zyw.spider.model.UrlNewsReader;

import java.io.IOException;

public class SpiderThread extends Thread {

    private String url;
    private SearchSate searchSate;

    public SpiderThread(SearchSate searchSate, String url) {
        this.url = url;
        this.searchSate = searchSate;
        //System.out.println("Start reading URL: " + this.url);
        start();
    }

    @Override
    public void run() {
        try {
            NewsWithRelated next = UrlNewsReader.read(url);
            searchSate.visit(next);
        } catch (IOException e) {
            System.out.println("Ignored an error page: " + url);
        }
    }
}
