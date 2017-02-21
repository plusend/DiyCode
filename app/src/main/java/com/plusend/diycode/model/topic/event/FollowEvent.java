package com.plusend.diycode.model.topic.event;

/**
 * Created by plusend on 2016/12/6.
 */

public class FollowEvent {
  private boolean result;

  public FollowEvent(boolean result) {
    this.result = result;
  }

  public boolean isResult() {
    return result;
  }
}
