package com.plusend.diycode.model.news.data;

import com.plusend.diycode.model.news.entity.News;
import com.plusend.diycode.util.Constant;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

interface NewsService {

  /**
   * 获取 news 列表
   */
  @GET("news.json") Call<List<News>> readNews(@Query("node_id") Integer nodeId,
      @Query("offset") Integer offset, @Query("limit") Integer limit);

  /**
   * 创建 News
   */
  @POST("news.json") @FormUrlEncoded Call<News> createNews(@Header(Constant.KEY_TOKEN) String token,
      @Field("title") String title, @Field("address") String address,
      @Field("node_id") Integer node_id);
}
