package com.plusend.diycode.view.adapter.notification;

public class NotificationFollow {
  private String avatarUrl;
  private String login;

  public NotificationFollow(String avatarUrl, String login) {
    this.avatarUrl = avatarUrl;
    this.login = login;
  }

  public String getAvatarUrl() {
    return avatarUrl;
  }

  public String getLogin() {
    return login;
  }
}