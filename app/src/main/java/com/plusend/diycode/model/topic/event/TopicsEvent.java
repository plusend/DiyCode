package com.plusend.diycode.model.topic.event;

import com.plusend.diycode.model.topic.entity.Topic;
import java.util.List;

/**
 * Created by plusend on 2016/11/24.
 */

public class TopicsEvent {
    private List<Topic> topicList;

    public TopicsEvent(List<Topic> topicList) {
        this.topicList = topicList;
    }

    public List<Topic> getTopicList() {
        return topicList;
    }
}
