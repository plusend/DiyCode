package com.plusend.diycode.mvp.model.topic.event;

import com.plusend.diycode.mvp.model.topic.entity.Reply;
import java.util.List;

/**
 * Created by plusend on 2016/12/18.
 */

public class RepliesEvent {
  private List<Reply> replyList;

  public RepliesEvent(List<Reply> replyList) {
    this.replyList = replyList;
  }

  public List<Reply> getReplyList() {
    return replyList;
  }
}
