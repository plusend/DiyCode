package com.plusend.diycode.event;

import com.plusend.diycode.mvp.model.user.entity.UserDetailInfo;

public class UserDetailInfoEvent {
  private UserDetailInfo userDetailInfo;

  public UserDetailInfoEvent(UserDetailInfo userDetailInfo) {
    this.userDetailInfo = userDetailInfo;
  }

  public UserDetailInfo getUserDetailInfo() {
    return userDetailInfo;
  }
}
