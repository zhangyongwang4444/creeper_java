package com.zyw.spider;

import com.zyw.spider.model.NewsWithRelated;
import com.zyw.spider.model.UrlNewsReader;
import com.zyw.spider.model.Viewable;
import com.zyw.spider.view.ListViewer;

import java.util.*;

public class Main {

    // 本地存储新闻内容，如何在终端显示

    // 1. 抽象出 对象
    // 2. 设计 对象应该具有的属性，状态和行为
    // 3. 思考 对象之间交互
    // 4. 开始写代码

    private static final int maximumURL = 10;

    public static void main(String[] args) throws Exception {
        long startTime = System.currentTimeMillis();
        // 广度优先搜索
        Queue<NewsWithRelated> newsQueue = new LinkedList<NewsWithRelated>();

        String startUrl = "https://readhub.me/topic/5bMmlAm75lD";   // 爬这里的内容 ....
        NewsWithRelated startNews = UrlNewsReader.read(startUrl);

        int count = 0;
        Set<String> visited = new HashSet<>();  // 标记 已经访问过的 URL
        visited.add(startUrl);
        newsQueue.add(startNews);
        ArrayList<Viewable> results = new ArrayList<>();  // 存储 扫描到的内容 （多态）

        // 广度优先搜索 的模板
        while (!newsQueue.isEmpty() && count <= maximumURL) {
            NewsWithRelated current = newsQueue.poll();
            results.add(current);
            count += 1;
            for (Map.Entry<String, String> entry : current.getRelateds().entrySet()) {
                String url = entry.getValue();
                NewsWithRelated next = UrlNewsReader.read(url);
                if (!visited.contains(url)) {
                    newsQueue.add(next);
                    visited.add(url);
                }
            }
        }


        long endTime = System.currentTimeMillis();

        new ListViewer(results).display();

        System.out.println("程序运行时间： " + (endTime - startTime) + "ms");
    }
}

