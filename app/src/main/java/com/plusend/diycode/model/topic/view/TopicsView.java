package com.plusend.diycode.model.topic.view;

import com.plusend.diycode.model.base.BaseView;
import com.plusend.diycode.model.topic.entity.Topic;
import java.util.List;

/**
 * Created by plusend on 2016/11/24.
 */

public interface TopicsView extends BaseView {
    void showTopics(List<Topic> topicList);

    void showTopTopics(List<Topic> topicList);
}
