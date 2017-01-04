package com.plusend.diycode.mvp.model.topic.event;

import com.plusend.diycode.mvp.model.topic.entity.TopicDetail;

/**
 * Created by plusend on 2016/11/30.
 */

public class NewTopicEvent {
  private TopicDetail topicDetail;

  public NewTopicEvent(TopicDetail topicDetail) {
    this.topicDetail = topicDetail;
  }

  public TopicDetail getTopicDetail() {
    return topicDetail;
  }
}
