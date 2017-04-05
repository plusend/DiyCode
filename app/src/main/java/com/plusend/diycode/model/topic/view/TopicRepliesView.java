package com.plusend.diycode.model.topic.view;

import com.plusend.diycode.model.base.BaseView;
import com.plusend.diycode.model.topic.entity.TopicReply;
import java.util.List;

/**
 * Created by plusend on 2016/11/26.
 */

public interface TopicRepliesView extends BaseView {
    void showReplies(List<TopicReply> topicReplyList);

    void addReplies(List<TopicReply> topicReplyList);

    void showNewReply();
}
