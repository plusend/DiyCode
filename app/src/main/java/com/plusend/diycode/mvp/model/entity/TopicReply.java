package com.plusend.diycode.mvp.model.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by plusend on 2016/11/24.
 */

public class TopicReply {
  @SerializedName("id") private int id;
  @SerializedName("body_html") private String bodyHtml;
  @SerializedName("created_at") private String createdAt;
  @SerializedName("updated_at") private String updatedAt;
  @SerializedName("deleted") private boolean deleted;
  @SerializedName("topic_id") private int topicId;
  @SerializedName("user") private User user;
  @SerializedName("likes_count") private int likesCount;
  @SerializedName("abilities") private Abilities abilities;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getBodyHtml() {
    return bodyHtml;
  }

  public void setBodyHtml(String bodyHtml) {
    this.bodyHtml = bodyHtml;
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

  public boolean isDeleted() {
    return deleted;
  }

  public void setDeleted(boolean deleted) {
    this.deleted = deleted;
  }

  public int getTopicId() {
    return topicId;
  }

  public void setTopicId(int topicId) {
    this.topicId = topicId;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public int getLikesCount() {
    return likesCount;
  }

  public void setLikesCount(int likesCount) {
    this.likesCount = likesCount;
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

    @Override public String toString() {
      return "User{" +
          "id=" + id +
          ", login='" + login + '\'' +
          ", name='" + name + '\'' +
          ", avatarUrl='" + avatarUrl + '\'' +
          '}';
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

    @Override public String toString() {
      return "Abilities{" +
          "update=" + update +
          ", destroy=" + destroy +
          '}';
    }
  }

  @Override public String toString() {
    return "TopicReply{" +
        "id=" + id +
        ", bodyHtml='" + bodyHtml + '\'' +
        ", createdAt='" + createdAt + '\'' +
        ", updatedAt='" + updatedAt + '\'' +
        ", deleted=" + deleted +
        ", topicId=" + topicId +
        ", user=" + user +
        ", likesCount=" + likesCount +
        ", abilities=" + abilities +
        '}';
  }
}
