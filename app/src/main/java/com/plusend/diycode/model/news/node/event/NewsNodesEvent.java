package com.plusend.diycode.model.news.node.event;

import com.plusend.diycode.model.news.node.entity.NewsNode;
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
