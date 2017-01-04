package com.plusend.diycode.mvp.model.topic.view;

import com.plusend.diycode.mvp.model.topic.entity.TopicReply;
import com.plusend.diycode.mvp.model.base.BaseView;
import java.util.List;

/**
 * Created by plusend on 2016/11/26.
 */

public interface TopicRepliesView extends BaseView {
  void showReplies(List<TopicReply> topicReplyList);
  void addReplies(List<TopicReply> topicReplyList);
}
