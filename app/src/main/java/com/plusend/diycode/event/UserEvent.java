package com.plusend.diycode.event;

import com.plusend.diycode.mvp.model.entity.User;

/**
 * Created by plusend on 2016/11/28.
 */

public class UserEvent {
  private User user;

  public UserEvent(User user) {
    this.user = user;
  }

  public User getUser() {
    return user;
  }
}
