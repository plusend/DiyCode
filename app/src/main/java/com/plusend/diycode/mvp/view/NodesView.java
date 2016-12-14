package com.plusend.diycode.mvp.view;

import com.plusend.diycode.mvp.model.entity.Node;
import java.util.List;

/**
 * Created by plusend on 2016/12/5.
 */

public interface NodesView extends BaseView{
  void showNodes(List<Node> nodeList);
}
