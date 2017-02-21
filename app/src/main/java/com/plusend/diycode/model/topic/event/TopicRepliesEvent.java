package com.plusend.diycode.model.topic.event;

import com.plusend.diycode.model.topic.entity.TopicReply;
import java.util.List;

/**
 * Created by plusend on 2016/11/26.
 */

public class TopicRepliesEvent {
  private List<TopicReply> topicReplyList;

  public TopicRepliesEvent(List<TopicReply> topicReplyList) {
    this.topicReplyList = topicReplyList;
  }

  public List<TopicReply> getTopicReplyList() {
    return topicReplyList;
  }
}
