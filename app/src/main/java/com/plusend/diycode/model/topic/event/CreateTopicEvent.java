package com.plusend.diycode.model.topic.event;

import com.plusend.diycode.model.topic.entity.TopicDetail;

/**
 * Created by plusend on 2016/11/30.
 */

public class CreateTopicEvent {
  private TopicDetail topicDetail;

  public CreateTopicEvent(TopicDetail topicDetail) {
    this.topicDetail = topicDetail;
  }

  public TopicDetail getTopicDetail() {
    return topicDetail;
  }
}
