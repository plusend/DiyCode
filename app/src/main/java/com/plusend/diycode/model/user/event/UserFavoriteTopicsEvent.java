package com.plusend.diycode.model.user.event;

import com.plusend.diycode.model.topic.entity.Topic;
import java.util.List;

public class UserFavoriteTopicsEvent {
    private List<Topic> topicList;

    public UserFavoriteTopicsEvent(List<Topic> topicList) {
        this.topicList = topicList;
    }

    public List<Topic> getTopicList() {
        return topicList;
    }
}
