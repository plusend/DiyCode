package com.plusend.diycode.model.user.event;

import com.plusend.diycode.model.user.entity.UserInfo;
import java.util.List;

public class UserFollowersEvent {
  private List<UserInfo> userInfoList;

  public UserFollowersEvent(List<UserInfo> userInfoList) {
    this.userInfoList = userInfoList;
  }

  public List<UserInfo> getUserInfoList() {
    return userInfoList;
  }
}
