package com.plusend.diycode.mvp.model.topic.event;

/**
 * Created by plusend on 2016/12/6.
 */

public class UnFavoriteEvent {
  private boolean result;

  public UnFavoriteEvent(boolean result) {
    this.result = result;
  }

  public boolean isResult() {
    return result;
  }
}
