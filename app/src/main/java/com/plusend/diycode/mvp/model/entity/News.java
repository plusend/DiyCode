package com.plusend.diycode.mvp.model.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by plusend on 2016/12/2.
 */

public class News {
  @SerializedName("id") private int id;
  @SerializedName("title") private String title;
  @SerializedName("created_at") private String createdAt;
  @SerializedName("updated_at") private String updatedAt;
  @SerializedName("user") private User user;
  @SerializedName("node_name") private String nodeName;
  @SerializedName("node_id") private int nodeId;
  @SerializedName("last_reply_user_id") private Object lastReplyUserId;
  @SerializedName("last_reply_user_login") private Object lastReplyUserLogin;
  @SerializedName("replied_at") private Object repliedAt;
  @SerializedName("address") private String address;
  @SerializedName("replies_count") private int repliesCount;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }

  public String getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(String updatedAt) {
    this.updatedAt = updatedAt;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public String getNodeName() {
    return nodeName;
  }

  public void setNodeName(String nodeName) {
    this.nodeName = nodeName;
  }

  public int getNodeId() {
    return nodeId;
  }

  public void setNodeId(int nodeId) {
    this.nodeId = nodeId;
  }

  public Object getLastReplyUserId() {
    return lastReplyUserId;
  }

  public void setLastReplyUserId(Object lastReplyUserId) {
    this.lastReplyUserId = lastReplyUserId;
  }

  public Object getLastReplyUserLogin() {
    return lastReplyUserLogin;
  }

  public void setLastReplyUserLogin(Object lastReplyUserLogin) {
    this.lastReplyUserLogin = lastReplyUserLogin;
  }

  public Object getRepliedAt() {
    return repliedAt;
  }

  public void setRepliedAt(Object repliedAt) {
    this.repliedAt = repliedAt;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public int getRepliesCount() {
    return repliesCount;
  }

  public void setRepliesCount(int repliesCount) {
    this.repliesCount = repliesCount;
  }

  public static class User {
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
  }
}
