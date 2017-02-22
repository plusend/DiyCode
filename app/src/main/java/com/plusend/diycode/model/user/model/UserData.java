package com.plusend.diycode.model.user.model;

import com.plusend.diycode.model.base.BaseData;

interface UserData extends BaseData {

  /**
   * 验证账号
   */
  void getToken(String username, String password);

  /**
   * token 过期时获取新的 token
   *
   * @param refresh_token 验证账号时获取的 refresh_token
   */
  void refreshToken(String refresh_token);

  /**
   * 获取当然登录者的资料
   */
  void getMe();

  /**
   * 获取用户详细资料
   *
   * @param loginName 用户登录名
   */
  void getUser(String loginName);

  /**
   * 获取用户屏蔽的用户
   *
   * @param loginName 用户登录名
   */
  void getUserBlocked(String token, String loginName, Integer offset, Integer limit);

  /**
   * 获取用户正在关注的人
   *
   * @param loginName 用户登录名
   */
  void getUserFollowing(String token, String loginName, Integer offset, Integer limit);

  /**
   * 获取用户的关注者列表
   *
   * @param loginName 用户登录名
   */
  void getUserFollowers(String token, String loginName, Integer offset, Integer limit);

  /**
   * 屏蔽用户
   */
  void blockUser(String token, String loginName);

  /**
   * 取消屏蔽用户
   */
  void unBlockUser(String token, String loginName);

  /**
   * 关注用户
   */
  void followUser(String token, String loginName);

  /**
   * 取消关注用户
   */
  void unFollowUser(String token, String loginName);

  /**
   * 收藏话题
   */
  void favoriteTopic(String token, int id);

  /**
   * 取消收藏话题
   */
  void unFavoriteTopic(String token, int id);

  /**
   * 关注话题
   */
  void followTopic(String token, int id);

  /**
   * 取消关注话题
   */
  void unFollowTopic(String token, int id);

  /**
   * 获取用户创建的话题列表
   *
   * @param loginName 用户的登录名
   */
  void getUserCreateTopics(String loginName, Integer offset, Integer limit);

  /**
   * 获取用户收藏的话题列表
   *
   * @param loginName 用户的登录名
   */
  void getUserFavoriteTopics(String loginName, Integer offset, Integer limit);

  /**
   * 获取用户创建的回帖列表
   *
   * @param loginName 用户的登录名
   * @param offset 默认 0，从第 21 条开始就传 20
   * @param limit 默认 20 范围 [1..150]
   */
  void getUserReplies(String loginName, Integer offset, Integer limit);
}
