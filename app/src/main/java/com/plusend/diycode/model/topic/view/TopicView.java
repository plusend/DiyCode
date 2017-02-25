package com.plusend.diycode.model.topic.view;

import com.plusend.diycode.model.topic.entity.TopicDetail;
import com.plusend.diycode.model.base.BaseView;

public interface TopicView extends BaseView {
  void showTopic(TopicDetail topicDetail);

  void loadTopicFinish();

  void showFavorite(boolean bool);

  void showUnFavorite(boolean bool);

  void showFollow(boolean bool);

  void showUnFollow(boolean bool);

  void showLike(boolean bool);

  void showUnLike(boolean bool);
}
