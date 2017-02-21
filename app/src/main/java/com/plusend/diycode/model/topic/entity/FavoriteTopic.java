package com.plusend.diycode.model.topic.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by plusend on 2016/12/6.
 */

public class FavoriteTopic {
  @SerializedName("ok") private int ok;

  public int getOk() {
    return ok;
  }

  public void setOk(int ok) {
    this.ok = ok;
  }
}
