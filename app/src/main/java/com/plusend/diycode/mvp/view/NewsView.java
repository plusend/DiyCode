package com.plusend.diycode.mvp.view;

import com.plusend.diycode.mvp.model.entity.News;
import java.util.List;

/**
 * Created by plusend on 2016/12/2.
 */

public interface NewsView extends BaseView {
  void showNews(List<News>newsList);
}
