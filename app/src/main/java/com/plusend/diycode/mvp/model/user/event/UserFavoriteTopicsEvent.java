package com.plusend.diycode.mvp.model.user.event;

import com.plusend.diycode.mvp.model.topic.entity.Topic;
import java.util.List;

/**
 * Created by plusend on 2016/11/29.
 */

public class UserFavoriteTopicsEvent {
  private List<Topic> topicList;

  public UserFavoriteTopicsEvent(List<Topic> topicList) {
    this.topicList = topicList;
  }

  public List<Topic> getTopicList() {
    return topicList;
  }
}
