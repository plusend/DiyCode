package com.plusend.diycode.model.news.node.view;

import com.plusend.diycode.model.base.BaseView;
import com.plusend.diycode.model.news.node.entity.NewsNode;
import java.util.List;

/**
 * Created by plusend on 2016/12/5.
 */

public interface NewsNodesView extends BaseView {
  void showNodes(List<NewsNode> newsNodeList);
}
