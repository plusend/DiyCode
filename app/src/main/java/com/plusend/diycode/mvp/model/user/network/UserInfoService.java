package com.plusend.diycode.mvp.model.user.network;

import com.plusend.diycode.mvp.model.entity.FavoriteTopic;
import com.plusend.diycode.mvp.model.entity.FollowTopic;
import com.plusend.diycode.mvp.model.entity.Topic;
import com.plusend.diycode.mvp.model.entity.UnFavoriteTopic;
import com.plusend.diycode.mvp.model.entity.UnFollowTopic;
import com.plusend.diycode.mvp.model.user.entity.UserDetailInfo;
import com.plusend.diycode.mvp.model.user.entity.UserInfo;
import com.plusend.diycode.mvp.model.user.entity.BlockUser;
import com.plusend.diycode.mvp.model.user.entity.FollowUser;
import com.plusend.diycode.mvp.model.user.entity.UnBlockUser;
import com.plusend.diycode.mvp.model.user.entity.UnFollowUser;
import com.plusend.diycode.util.Constant;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserInfoService {
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
   * 屏蔽用户
   */
  @POST("users/{login}/block.json") Call<BlockUser> blockUser(
      @Header(Constant.KEY_TOKEN) String token, @Path("loginName") String loginName);

  /**
   * 取消屏蔽用户
   */
  @POST("users/{login}/unblock.json") Call<UnBlockUser> unBlockUser(
      @Header(Constant.KEY_TOKEN) String token, @Path("loginName") String loginName);

  /**
   * 关注用户
   */
  @POST("users/{login}/follow.json") Call<FollowUser> followUser(
      @Header(Constant.KEY_TOKEN) String token, @Path("loginName") String loginName);

  /**
   * 取消关注用户
   */
  @POST("users/{login}/unfollow.json") Call<UnFollowUser> unFollowUser(
      @Header(Constant.KEY_TOKEN) String token, @Path("loginName") String loginName);

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
}
