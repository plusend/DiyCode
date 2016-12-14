package com.plusend.diycode.event;

import com.plusend.diycode.mvp.model.entity.Node;
import java.util.List;

/**
 * Created by plusend on 2016/12/5.
 */

public class NodesEvent {
  private List<Node> nodeList;

  public NodesEvent(List<Node> nodeList) {
    this.nodeList = nodeList;
  }

  public List<Node> getNodeList() {
    return nodeList;
  }
}
