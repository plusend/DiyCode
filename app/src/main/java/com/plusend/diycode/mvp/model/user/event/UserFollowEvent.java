package com.plusend.diycode.mvp.model.user.event;

import com.plusend.diycode.mvp.model.user.entity.UserFollow;

public class UserFollowEvent {
  private UserFollow userFollow;

  public UserFollowEvent(UserFollow userFollow) {
    this.userFollow = userFollow;
  }

  public UserFollow getUserFollow() {
    return userFollow;
  }
}
