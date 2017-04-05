package com.plusend.diycode.model.news.view;

import com.plusend.diycode.model.base.BaseView;
import com.plusend.diycode.model.news.entity.News;
import java.util.List;

/**
 * Created by plusend on 2016/12/2.
 */

public interface NewsView extends BaseView {
    void showNews(List<News> newsList);
}
