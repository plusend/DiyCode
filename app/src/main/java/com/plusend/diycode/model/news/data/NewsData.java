package com.plusend.diycode.model.news.data;

import com.plusend.diycode.model.base.BaseData;

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
