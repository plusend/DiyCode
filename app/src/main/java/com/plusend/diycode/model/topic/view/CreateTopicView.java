package com.plusend.diycode.model.topic.view;

import com.plusend.diycode.model.base.BaseView;
import com.plusend.diycode.model.topic.entity.TopicDetail;

public interface CreateTopicView extends BaseView {
    void getNewTopic(TopicDetail topicDetail);
}
