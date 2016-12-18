package com.plusend.diycode.event;

import com.plusend.diycode.mvp.model.entity.User;

/**
 * Created by plusend on 2016/11/28.
 */

public class MeEvent {
  private User user;

  public MeEvent(User user) {
    this.user = user;
  }

  public User getUser() {
    return user;
  }
}
