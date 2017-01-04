package com.plusend.diycode.mvp.model.user.model;

import com.plusend.diycode.mvp.model.entity.Token;
import com.plusend.diycode.mvp.model.topic.entity.Reply;
import com.plusend.diycode.mvp.model.topic.entity.Topic;
import com.plusend.diycode.mvp.model.user.entity.UserBlock;
import com.plusend.diycode.mvp.model.user.entity.UserDetailInfo;
import com.plusend.diycode.mvp.model.user.entity.UserFollow;
import com.plusend.diycode.mvp.model.user.entity.UserInfo;
import com.plusend.diycode.mvp.model.user.entity.UserUnBlock;
import com.plusend.diycode.mvp.model.user.entity.UserUnFollow;
import com.plusend.diycode.util.Constant;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserService {
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
   * 获取用户屏蔽的用户
   *
   * @param loginName 用户登录名
   */
  @GET("users/{loginName}/blocked.json") Call<List<UserInfo>> getUserBlocked(
      @Header(Constant.KEY_TOKEN) String token, @Path("loginName") String loginName,
      @Query("offset") Integer offset, @Query("limit") Integer limit);

  /**
   * 获取用户正在关注的人
   *
   * @param loginName 用户登录名
   */
  @GET("users/{loginName}/following.json") Call<List<UserInfo>> getUserFollowing(
      @Header(Constant.KEY_TOKEN) String token, @Path("loginName") String loginName,
      @Query("offset") Integer offset, @Query("limit") Integer limit);

  /**
   * 获取用户的关注者列表
   *
   * @param loginName 用户登录名
   */
  @GET("users/{loginName}/followers.json") Call<List<UserInfo>> getUserFollowers(
      @Header(Constant.KEY_TOKEN) String token, @Path("loginName") String loginName,
      @Query("offset") Integer offset, @Query("limit") Integer limit);

  /**
   * 屏蔽用户
   */
  @POST("users/{login}/block.json") Call<UserBlock> blockUser(
      @Header(Constant.KEY_TOKEN) String token, @Path("loginName") String loginName);

  /**
   * 取消屏蔽用户
   */
  @POST("users/{login}/unblock.json") Call<UserUnBlock> unBlockUser(
      @Header(Constant.KEY_TOKEN) String token, @Path("loginName") String loginName);

  /**
   * 关注用户
   */
  @POST("users/{login}/follow.json") Call<UserFollow> followUser(
      @Header(Constant.KEY_TOKEN) String token, @Path("loginName") String loginName);

  /**
   * 取消关注用户
   */
  @POST("users/{login}/unfollow.json") Call<UserUnFollow> unFollowUser(
      @Header(Constant.KEY_TOKEN) String token, @Path("loginName") String loginName);

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
}
