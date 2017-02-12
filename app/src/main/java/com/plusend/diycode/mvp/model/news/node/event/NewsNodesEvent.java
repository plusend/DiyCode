package com.plusend.diycode.mvp.model.news.node.event;

import com.plusend.diycode.mvp.model.news.node.entity.NewsNode;
import java.util.List;

public class NewsNodesEvent {
  private List<NewsNode> newsNodeList;

  public NewsNodesEvent(List<NewsNode> newsNodeList) {
    this.newsNodeList = newsNodeList;
  }

  public List<NewsNode> getNewsNodeList() {
    return newsNodeList;
  }
}
