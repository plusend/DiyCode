package com.plusend.diycode.model.topic.node.event;

import com.plusend.diycode.model.topic.node.entity.Node;
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
