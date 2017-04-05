package com.plusend.diycode.model.news.event;

import com.plusend.diycode.model.news.entity.News;
import java.util.List;

/**
 * Created by plusend on 2016/12/2.
 */

public class NewsEvent {
    private List<News> newsList;

    public NewsEvent(List<News> newsList) {
        this.newsList = newsList;
    }

    public List<News> getNewsList() {
        return newsList;
    }
}
