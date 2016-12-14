package com.plusend.diycode.mvp.model;

/**
 * Created by plusend on 2016/11/24.
 */

public interface Data {
  /**
   * 验证账号
   */
  void getToken();

  /**
   * 获取当然登录者的资料
   */
  void getMe();

  /**
   * 获取用户详细资料
   */
  void getUser(String loginName);

  /**
   * 获取话题列表
   *
   * @param type 默认值 last_actived 范围 ["last_actived", "recent", "no_reply", "popular", "excellent"]
   * @param nodeId 如果你需要只看某个节点的，请传此参数
   * @param offset 默认 0，从第 21 条开始就传 20
   * @param limit 默认 20 范围 [1..150]
   */
  void getTopics(String type, Integer nodeId, Integer offset, Integer limit);

  /**
   * 获取话题详情
   *
   * @param id 帖子 id
   */
  void getTopic(int id);

  /**
   * 获取话题评论
   *
   * @param id 帖子 id
   * @param offset 默认 0
   * @param limit 默认 20 范围 [1..150]
   */
  void getReplies(int id, Integer offset, Integer limit);

  /**
   * 获取酷站信息
   */
  void getSite();

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
   * 创建话题
   *
   * @param title 话题标题
   * @param body 话题内容, Markdown 格式
   * @param nodeId 节点编号
   */
  void newTopic(String title, String body, int nodeId);

  /**
   * 收藏话题
   *
   * @param id 话题 id
   */
  void favorite(int id);

  /**
   * 取消收藏话题
   *
   * @param id 话题 id
   */
  void unFavorite(int id);

  /**
   * 关注话题
   *
   * @param id 话题 id
   */
  void follow(int id);

  /**
   * 取消关注话题
   *
   * @param id 话题 id
   */
  void unFollow(int id);

  /**
   * 创建回帖
   *
   * @param id 帖子 id
   * @param body 回帖内容, Markdown 格式
   */
  void createReply(int id, String body);

  /**
   * 获取 news 列表
   */
  void readNews(Integer nodeId, Integer offset, Integer limit);

  /**
   * 获取节点列表
   */
  void readNodes();

  /**
   * 获取当前用户的通知列表
   */
  void readNotifications(Integer offset, Integer limit);

  /**
   * 删除当前用户的某个通知
   */
  void deleteNotification(int id);

  /**
   * 删除当前用户的所有通知
   */
  void deleteAllNotifications();

  /**
   * 将当前用户的一些通知设成已读状态
   */
  void markNotificationsAsRead(int[] ids);

  /**
   * 获得未读通知数量
   */
  void unReadNotificationCount();
}
