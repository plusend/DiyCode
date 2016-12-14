package com.plusend.diycode.mvp.model.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by plusend on 2016/11/24.
 */

public class Topic {
  @SerializedName("id") private int id;
  @SerializedName("title") private String title;
  @SerializedName("created_at") private String createdAt;
  @SerializedName("updated_at") private String updatedAt;
  @SerializedName("replied_at") private String repliedAt;
  @SerializedName("replies_count") private int repliesCount;
  @SerializedName("node_name") private String nodeName;
  @SerializedName("node_id") private int nodeId;
  @SerializedName("last_reply_user_id") private int lastReplyUserId;
  @SerializedName("last_reply_user_login") private String lastReplyUserLogin;
  @SerializedName("user") private User user;
  @SerializedName("deleted") private boolean deleted;
  @SerializedName("excellent") private boolean excellent;
  @SerializedName("abilities") private Abilities abilities;

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

  public String getRepliedAt() {
    return repliedAt;
  }

  public void setRepliedAt(String repliedAt) {
    this.repliedAt = repliedAt;
  }

  public int getRepliesCount() {
    return repliesCount;
  }

  public void setRepliesCount(int repliesCount) {
    this.repliesCount = repliesCount;
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

  public int getLastReplyUserId() {
    return lastReplyUserId;
  }

  public void setLastReplyUserId(int lastReplyUserId) {
    this.lastReplyUserId = lastReplyUserId;
  }

  public String getLastReplyUserLogin() {
    return lastReplyUserLogin;
  }

  public void setLastReplyUserLogin(String lastReplyUserLogin) {
    this.lastReplyUserLogin = lastReplyUserLogin;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public boolean isDeleted() {
    return deleted;
  }

  public void setDeleted(boolean deleted) {
    this.deleted = deleted;
  }

  public boolean isExcellent() {
    return excellent;
  }

  public void setExcellent(boolean excellent) {
    this.excellent = excellent;
  }

  public Abilities getAbilities() {
    return abilities;
  }

  public void setAbilities(Abilities abilities) {
    this.abilities = abilities;
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

  public static class Abilities {
    @SerializedName("update") private boolean update;
    @SerializedName("destroy") private boolean destroy;

    public boolean isUpdate() {
      return update;
    }

    public void setUpdate(boolean update) {
      this.update = update;
    }

    public boolean isDestroy() {
      return destroy;
    }

    public void setDestroy(boolean destroy) {
      this.destroy = destroy;
    }
  }
}
