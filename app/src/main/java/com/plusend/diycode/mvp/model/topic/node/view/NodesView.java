package com.plusend.diycode.mvp.model.topic.node.view;

import com.plusend.diycode.mvp.model.topic.node.entity.Node;
import com.plusend.diycode.mvp.model.base.BaseView;
import java.util.List;

/**
 * Created by plusend on 2016/12/5.
 */

public interface NodesView extends BaseView {
  void showNodes(List<Node> nodeList);
}
