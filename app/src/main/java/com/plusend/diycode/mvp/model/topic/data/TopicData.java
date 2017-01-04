package com.plusend.diycode.mvp.model.topic.data;

import com.plusend.diycode.mvp.model.base.BaseData;

public interface TopicData extends BaseData {

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
}
