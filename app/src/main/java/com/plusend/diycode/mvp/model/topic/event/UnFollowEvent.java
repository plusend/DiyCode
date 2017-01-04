package com.plusend.diycode.mvp.model.topic.event;

/**
 * Created by plusend on 2016/12/6.
 */

public class UnFollowEvent {
  private boolean result;

  public UnFollowEvent(boolean result) {
    this.result = result;
  }

  public boolean isResult() {
    return result;
  }
}
