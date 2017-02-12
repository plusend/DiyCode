package com.plusend.diycode.mvp.model.news.event;

import com.plusend.diycode.mvp.model.news.entity.News;

public class CreateNewsEvent {
  private News news;

  public CreateNewsEvent(News news) {
    this.news = news;
  }

  public News getNews() {
    return news;
  }
}
