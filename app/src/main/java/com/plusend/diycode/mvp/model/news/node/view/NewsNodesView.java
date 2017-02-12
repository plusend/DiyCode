package com.plusend.diycode.mvp.model.news.node.view;

import com.plusend.diycode.mvp.model.base.BaseView;
import com.plusend.diycode.mvp.model.news.node.entity.NewsNode;
import java.util.List;

/**
 * Created by plusend on 2016/12/5.
 */

public interface NewsNodesView extends BaseView {
  void showNodes(List<NewsNode> newsNodeList);
}
