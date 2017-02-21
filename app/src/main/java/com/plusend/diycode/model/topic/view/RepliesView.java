package com.plusend.diycode.model.topic.view;

import com.plusend.diycode.model.topic.entity.Reply;
import com.plusend.diycode.model.base.BaseView;
import java.util.List;

/**
 * Created by plusend on 2016/12/18.
 */

public interface RepliesView extends BaseView {
  void showReplies(List<Reply> replyList);
}
