package com.plusend.diycode.model.user.event;

import com.plusend.diycode.model.user.entity.UserDetailInfo;

public class UserDetailInfoEvent {
  private UserDetailInfo userDetailInfo;

  public UserDetailInfoEvent(UserDetailInfo userDetailInfo) {
    this.userDetailInfo = userDetailInfo;
  }

  public UserDetailInfo getUserDetailInfo() {
    return userDetailInfo;
  }
}
