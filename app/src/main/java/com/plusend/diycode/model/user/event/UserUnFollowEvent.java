package com.plusend.diycode.model.user.event;

import com.plusend.diycode.model.user.entity.UserUnFollow;

public class UserUnFollowEvent {
  private UserUnFollow userUnFollow;

  public UserUnFollowEvent(UserUnFollow userUnFollow) {
    this.userUnFollow = userUnFollow;
  }

  public UserUnFollow getUserUnFollow() {
    return userUnFollow;
  }
}
