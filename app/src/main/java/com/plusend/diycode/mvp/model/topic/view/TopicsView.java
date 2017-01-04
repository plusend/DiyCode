package com.plusend.diycode.mvp.model.topic.view;

import com.plusend.diycode.mvp.model.topic.entity.Topic;
import com.plusend.diycode.mvp.model.base.BaseView;
import java.util.List;

/**
 * Created by plusend on 2016/11/24.
 */

public interface TopicsView extends BaseView {
  void showTopics(List<Topic> topicList);
}
