package com.plusend.diycode.model.user.event;

import com.plusend.diycode.model.user.entity.UserFollow;

public class UserFollowEvent {
  private UserFollow userFollow;

  public UserFollowEvent(UserFollow userFollow) {
    this.userFollow = userFollow;
  }

  public UserFollow getUserFollow() {
    return userFollow;
  }
}
