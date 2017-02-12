package com.plusend.diycode.mvp.model.news.data;

import com.plusend.diycode.mvp.model.entity.Token;
import com.plusend.diycode.mvp.model.news.entity.News;
import com.plusend.diycode.mvp.model.topic.node.entity.Node;
import com.plusend.diycode.mvp.model.notification.entity.Notification;
import com.plusend.diycode.mvp.model.notification.entity.NotificationDelete;
import com.plusend.diycode.mvp.model.notification.entity.NotificationsDelete;
import com.plusend.diycode.mvp.model.notification.entity.NotificationsRead;
import com.plusend.diycode.mvp.model.notification.entity.NotificationsUnreadCount;
import com.plusend.diycode.mvp.model.site.entity.Site;
import com.plusend.diycode.mvp.model.topic.entity.FavoriteTopic;
import com.plusend.diycode.mvp.model.topic.entity.FollowTopic;
import com.plusend.diycode.mvp.model.topic.entity.Reply;
import com.plusend.diycode.mvp.model.topic.entity.Topic;
import com.plusend.diycode.mvp.model.topic.entity.TopicDetail;
import com.plusend.diycode.mvp.model.topic.entity.TopicReply;
import com.plusend.diycode.mvp.model.topic.entity.UnFavoriteTopic;
import com.plusend.diycode.mvp.model.topic.entity.UnFollowTopic;
import com.plusend.diycode.mvp.model.user.entity.UserDetailInfo;
import com.plusend.diycode.util.Constant;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
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
