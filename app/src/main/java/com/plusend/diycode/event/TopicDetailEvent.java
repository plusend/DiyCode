package com.plusend.diycode.event;

import com.plusend.diycode.mvp.model.entity.TopicDetail;

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
