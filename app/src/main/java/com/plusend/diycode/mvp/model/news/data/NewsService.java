package com.plusend.diycode.mvp.model.news.data;

import com.plusend.diycode.mvp.model.entity.Token;
import com.plusend.diycode.mvp.model.news.entity.News;
import com.plusend.diycode.mvp.model.node.entity.Node;
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
   * 登录获取 Token
   */
  @POST("https://www.diycode.cc/oauth/token") @FormUrlEncoded Call<Token> getToken(
      @Field("client_id") String client_id, @Field("client_secret") String client_secret,
      @Field("grant_type") String grant_type, @Field("username") String username,
      @Field("password") String password);

  /**
   * 获取当然登录者的资料
   *
   * @param token 当然登录者的 Token
   */
  @GET("users/me.json") Call<UserDetailInfo> getMe(@Header(Constant.KEY_TOKEN) String token);

  /**
   * 获取用户详细资料
   *
   * @param loginName 用户登录名
   */
  @GET("users/{loginName}.json") Call<UserDetailInfo> getUser(
      @Header(Constant.KEY_TOKEN) String token, @Path("loginName") String loginName);

  /**
   * 获取话题列表
   *
   * @param type 默认值 last_actived 范围 ["last_actived", "recent", "no_reply", "popular", "excellent"]
   * @param nodeId 如果你需要只看某个节点的，请传此参数
   * @param offset 默认 0，从第 21 条开始就传 20
   * @param limit 默认 20 范围 [1..150]
   */
  @GET("topics.json") Call<List<Topic>> getTopics(@Query("type") String type,
      @Query("node_id") Integer nodeId, @Query("offset") Integer offset,
      @Query("limit") Integer limit);

  /**
   * 获取话题详情
   *
   * @param id 帖子 id
   */
  @GET("topics/{id}.json") Call<TopicDetail> getTopic(@Path("id") int id);

  /**
   * 获取话题评论
   *
   * @param id 帖子 id
   */
  @GET("topics/{id}/replies.json") Call<List<TopicReply>> getReplies(@Path("id") int id,
      @Query("offset") Integer offset, @Query("limit") Integer limit);

  /**
   * 获取酷站信息
   */
  @GET("sites.json") Call<List<Site>> getSite();

  /**
   * 获取用户创建的话题列表
   *
   * @param loginName 用户的登录名
   * @param offset 默认 0，从第 21 条开始就传 20
   * @param limit 默认 20 范围 [1..150]
   */
  @GET("users/{login}/topics.json") Call<List<Topic>> getUserCreateTopics(
      @Path("login") String loginName, @Query("offset") Integer offset,
      @Query("limit") Integer limit);

  /**
   * 获取用户收藏的话题列表
   *
   * @param loginName 用户的登录名
   * @param offset 默认 0，从第 21 条开始就传 20
   * @param limit 默认 20 范围 [1..150]
   */
  @GET("users/{login}/favorites.json") Call<List<Topic>> getUserFavoriteTopics(
      @Path("login") String loginName, @Query("offset") Integer offset,
      @Query("limit") Integer limit);

  /**
   * 获取用户创建的回帖列表
   *
   * @param loginName 用户的登录名
   * @param offset 默认 0，从第 21 条开始就传 20
   * @param limit 默认 20 范围 [1..150]
   */
  @GET("users/{login}/replies.json") Call<List<Reply>> getUserReplies(
      @Path("login") String loginName, @Query("offset") Integer offset,
      @Query("limit") Integer limit);

  /**
   * 创建话题
   *
   * @param title 话题标题
   * @param body 话题内容, Markdown 格式
   * @param nodeId 节点编号
   */
  @POST("topics.json") @FormUrlEncoded Call<TopicDetail> newTopic(
      @Header(Constant.KEY_TOKEN) String token, @Field("title") String title,
      @Field("body") String body, @Field("node_id") int nodeId);

  /**
   * 收藏话题
   */
  @POST("topics/{id}/favorite.json") Call<FavoriteTopic> favoriteTopic(
      @Header(Constant.KEY_TOKEN) String token, @Path("id") int id);

  /**
   * 取消收藏话题
   */
  @POST("topics/{id}/unfavorite.json") Call<UnFavoriteTopic> unFavoriteTopic(
      @Header(Constant.KEY_TOKEN) String token, @Path("id") int id);

  /**
   * 关注话题
   */
  @POST("topics/{id}/follow.json") Call<FollowTopic> followTopic(
      @Header(Constant.KEY_TOKEN) String token, @Path("id") int id);

  /**
   * 取消关注话题
   */
  @POST("topics/{id}/unfollow.json") Call<UnFollowTopic> unFollowTopic(
      @Header(Constant.KEY_TOKEN) String token, @Path("id") int id);

  /**
   * 创建回帖
   *
   * @param token 登录 Token
   * @param id 帖子 id
   * @param body 回帖内容, Markdown 格式
   */
  @POST("topics/{id}/replies.json") @FormUrlEncoded Call<TopicReply> createReply(
      @Header(Constant.KEY_TOKEN) String token, @Path("id") int id, @Field("body") String body);

  /**
   * 获取 news 列表
   */
  @GET("news.json") Call<List<News>> readNews(@Query("node_id") Integer nodeId,
      @Query("offset") Integer offset, @Query("limit") Integer limit);

  /**
   * 获取节点列表
   */
  @GET("nodes.json") Call<List<Node>> readNodes();

  /**
   * 获取当前用户的通知列表
   */
  @GET("notifications.json") Call<List<Notification>> readNotifications(
      @Header(Constant.KEY_TOKEN) String token, @Query("offset") Integer offset,
      @Query("limit") Integer limit);

  /**
   * 删除当前用户的某个通知
   */
  @DELETE("notifications/{id}.json") Call<NotificationDelete> deleteNotification(
      @Header(Constant.KEY_TOKEN) String token, @Path("id") int id);

  /**
   * 删除当前用户的所有通知
   */
  @DELETE("notifications/all.json") Call<NotificationsDelete> deleteAllNotifications(
      @Header(Constant.KEY_TOKEN) String token);

  /**
   * 将当前用户的一些通知设成已读状态
   */
  @POST("notifications/read.json") Call<NotificationsRead> markNotificationsAsRead(
      @Header(Constant.KEY_TOKEN) String token, @Query("ids") int[] ids);

  /**
   * 获得未读通知数量
   */
  @GET("notifications/unread_count.json") Call<NotificationsUnreadCount> unReadNotificationCount(
      @Header(Constant.KEY_TOKEN) String token);
}
