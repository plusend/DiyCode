package com.plusend.diycode.model.topic.event;

/**
 * Created by plusend on 2016/12/6.
 */

public class FavoriteEvent {
  private boolean result;

  public FavoriteEvent(boolean result) {
    this.result = result;
  }

  public boolean isResult() {
    return result;
  }
}
