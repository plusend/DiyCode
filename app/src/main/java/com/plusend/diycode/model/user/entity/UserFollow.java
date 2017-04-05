package com.plusend.diycode.model.user.entity;

import com.google.gson.annotations.SerializedName;

public class UserFollow {
    @SerializedName("ok") private int ok;

    public int getOk() {
        return ok;
    }

    public void setOk(int ok) {
        this.ok = ok;
    }
}
