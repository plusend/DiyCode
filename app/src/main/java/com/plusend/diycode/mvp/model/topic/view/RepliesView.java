package com.plusend.diycode.mvp.model.topic.view;

import com.plusend.diycode.mvp.model.topic.entity.Reply;
import com.plusend.diycode.mvp.model.base.BaseView;
import java.util.List;

/**
 * Created by plusend on 2016/12/18.
 */

public interface RepliesView extends BaseView {
  void showReplies(List<Reply> replyList);
}
