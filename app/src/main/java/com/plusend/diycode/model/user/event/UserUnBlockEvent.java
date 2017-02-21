package com.plusend.diycode.model.user.event;

import com.plusend.diycode.model.user.entity.UserUnBlock;

public class UserUnBlockEvent {
  private UserUnBlock userUnBlock;

  public UserUnBlockEvent(UserUnBlock userUnBlock) {
    this.userUnBlock = userUnBlock;
  }

  public UserUnBlock getUserUnBlock() {
    return userUnBlock;
  }
}
