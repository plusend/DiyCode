package com.plusend.diycode.mvp.view;

import com.plusend.diycode.mvp.model.entity.TopicDetail;

/**
 * Created by plusend on 2016/11/25.
 */

public interface TopicView extends BaseView {
  void showTopic(TopicDetail topicDetail);
}
