package com.plusend.diycode.view.adapter.notification;

public class NotificationReply {
  private String avatarUrl;
  private String login;
  private String topicTitle;
  private String bodyHtml;
  private int topicId;

  public NotificationReply(String avatarUrl, String login, String topicTitle, String bodyHtml,
      int topicId) {
    this.avatarUrl = avatarUrl;
    this.login = login;
    this.topicTitle = topicTitle;
    this.bodyHtml = bodyHtml;
    this.topicId = topicId;
  }

  public String getAvatarUrl() {
    return avatarUrl;
  }

  public String getLogin() {
    return login;
  }

  public String getTopicTitle() {
    return topicTitle;
  }

  public String getBodyHtml() {
    return bodyHtml;
  }

  public int getTopicId() {
    return topicId;
  }
}