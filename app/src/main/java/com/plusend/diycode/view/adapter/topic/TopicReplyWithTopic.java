package com.plusend.diycode.view.adapter.topic;

import com.plusend.diycode.mvp.model.topic.entity.TopicDetail;
import com.plusend.diycode.mvp.model.topic.entity.TopicReply;

public class TopicReplyWithTopic {
  private TopicDetail topicDetail;
  private TopicReply topicReply;

  public TopicReplyWithTopic(TopicDetail topicDetail, TopicReply topicReply) {
    this.topicDetail = topicDetail;
    this.topicReply = topicReply;
  }

  public TopicDetail getTopicDetail() {
    return topicDetail;
  }

  public TopicReply getTopicReply() {
    return topicReply;
  }
}
