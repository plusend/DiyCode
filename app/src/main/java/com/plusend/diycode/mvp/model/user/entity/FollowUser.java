package com.plusend.diycode.mvp.model.user.entity;

import com.google.gson.annotations.SerializedName;

public class FollowUser {
  @SerializedName("ok") private int ok;

  public int getOk() {
    return ok;
  }

  public void setOk(int ok) {
    this.ok = ok;
  }
}
