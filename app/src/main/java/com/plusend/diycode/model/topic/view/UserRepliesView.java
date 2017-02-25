package com.plusend.diycode.model.topic.view;

import com.plusend.diycode.model.topic.entity.Reply;
import com.plusend.diycode.model.base.BaseView;
import java.util.List;

public interface UserRepliesView extends BaseView {
  void showReplies(List<Reply> replyList);
}
