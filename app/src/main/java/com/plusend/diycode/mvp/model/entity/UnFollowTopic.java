package com.plusend.diycode.mvp.model.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by plusend on 2016/12/6.
 */

public class UnFollowTopic {
  @SerializedName("ok") private int ok;

  public int getOk() {
    return ok;
  }

  public void setOk(int ok) {
    this.ok = ok;
  }
}
