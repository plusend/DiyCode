package com.plusend.diycode.mvp.view;

import com.plusend.diycode.mvp.model.entity.Reply;
import java.util.List;

/**
 * Created by plusend on 2016/12/18.
 */

public interface RepliesView extends BaseView {
  void showReplies(List<Reply> replyList);
}
