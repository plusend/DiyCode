package com.plusend.diycode.mvp.model.news.view;

import com.plusend.diycode.mvp.model.base.BaseView;
import com.plusend.diycode.mvp.model.news.entity.News;

public interface CreateNewsView extends BaseView {
  void showNews(News news);
}
