package com.plusend.diycode.mvp.model.topic.event;

import com.plusend.diycode.mvp.model.topic.entity.TopicDetail;

/**
 * Created by plusend on 2016/11/25.
 */

public class TopicDetailEvent {
  private TopicDetail topicDetail;

  public TopicDetailEvent(TopicDetail topicDetail) {
    this.topicDetail = topicDetail;
  }

  public TopicDetail getTopicDetail() {
    return topicDetail;
  }
}
