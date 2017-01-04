package com.plusend.diycode.mvp.model.user.entity;

import com.google.gson.annotations.SerializedName;

/**
 * 用于记录用户收藏的用户，屏蔽的用户等
 */
public class UserInfo {

  @SerializedName("id") private int id;
  @SerializedName("login") private String login;
  @SerializedName("name") private String name;
  @SerializedName("avatar_url") private String avatarUrl;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAvatarUrl() {
    return avatarUrl;
  }

  public void setAvatarUrl(String avatarUrl) {
    this.avatarUrl = avatarUrl;
  }

  @Override public String toString() {
    return "UserInfo{" +
        "id=" + id +
        ", login='" + login + '\'' +
        ", name='" + name + '\'' +
        ", avatarUrl='" + avatarUrl + '\'' +
        '}';
  }
}
