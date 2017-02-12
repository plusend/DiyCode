package com.plusend.diycode.mvp.model.news.node.data;

import com.plusend.diycode.mvp.model.news.node.entity.NewsNode;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

interface NewsNodeService {

  /**
   * 获取节点列表
   */
  @GET("news/nodes.json") Call<List<NewsNode>> readNewsNodes();
}
