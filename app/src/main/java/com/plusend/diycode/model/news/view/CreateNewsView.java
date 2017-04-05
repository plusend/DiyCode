package com.plusend.diycode.model.news.view;

import com.plusend.diycode.model.base.BaseView;
import com.plusend.diycode.model.news.entity.News;

public interface CreateNewsView extends BaseView {
    void showNews(News news);
}
