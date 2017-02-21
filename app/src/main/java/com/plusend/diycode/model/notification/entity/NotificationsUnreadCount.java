package com.plusend.diycode.model.notification.entity;

import com.google.gson.annotations.SerializedName;

public class NotificationsUnreadCount {

  @SerializedName("count") private int count;

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }
}
