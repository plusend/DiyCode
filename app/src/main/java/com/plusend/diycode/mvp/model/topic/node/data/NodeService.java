package com.plusend.diycode.mvp.model.topic.node.data;

import com.plusend.diycode.mvp.model.topic.node.entity.Node;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

interface NodeService {

  /**
   * 获取节点列表
   */
  @GET("nodes.json") Call<List<Node>> readNodes();
}
