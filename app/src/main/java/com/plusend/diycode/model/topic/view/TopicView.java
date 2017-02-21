package com.plusend.diycode.model.topic.view;

import com.plusend.diycode.model.topic.entity.TopicDetail;
import com.plusend.diycode.model.base.BaseView;

/**
 * Created by plusend on 2016/11/25.
 */

public interface TopicView extends BaseView {
  void showTopic(TopicDetail topicDetail);
  void loadTopicFinish();
}
