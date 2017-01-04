package com.plusend.diycode.mvp.model.news.view;

import com.plusend.diycode.mvp.model.news.entity.News;
import com.plusend.diycode.mvp.model.base.BaseView;
import java.util.List;

/**
 * Created by plusend on 2016/12/2.
 */

public interface NewsView extends BaseView {
  void showNews(List<News>newsList);
}
