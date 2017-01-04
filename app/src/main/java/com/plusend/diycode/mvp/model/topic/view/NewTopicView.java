package com.plusend.diycode.mvp.model.topic.view;

import com.plusend.diycode.mvp.model.topic.entity.TopicDetail;
import com.plusend.diycode.mvp.model.base.BaseView;

/**
 * Created by plusend on 2016/11/30.
 */

public interface NewTopicView extends BaseView {
  void getNewTopic(TopicDetail topicDetail);
}
