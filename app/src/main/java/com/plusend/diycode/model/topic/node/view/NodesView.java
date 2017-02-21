package com.plusend.diycode.model.topic.node.view;

import com.plusend.diycode.model.topic.node.entity.Node;
import com.plusend.diycode.model.base.BaseView;
import java.util.List;

/**
 * Created by plusend on 2016/12/5.
 */

public interface NodesView extends BaseView {
  void showNodes(List<Node> nodeList);
}
