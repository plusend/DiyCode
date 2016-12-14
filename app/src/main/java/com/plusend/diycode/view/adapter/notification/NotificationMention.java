package com.plusend.diycode.view.adapter.notification;

/**
 * Created by plusend on 2016/12/13.
 */
public class NotificationMention {
  private String avatarUrl;
  private String login;
  private String topicTitle;
  private String bodyHtml;
  private int topicId;

  public NotificationMention(String avatarUrl, String login, String topicTitle, String bodyHtml,
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