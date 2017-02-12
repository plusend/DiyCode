package com.plusend.diycode.mvp.model.news.data;

import com.plusend.diycode.mvp.model.base.BaseData;

public interface NewsData extends BaseData {

  /**
   * 获取 news 列表
   */
  void readNews(Integer nodeId, Integer offset, Integer limit);

  /**
   * 创建 news
   */
  void createNews(String title, String address, Integer node_id);
}
