package com.plusend.diycode.mvp.model.entity;

import com.google.gson.annotations.SerializedName;
import com.plusend.diycode.mvp.model.user.entity.UserDetailInfo;

/**
 * Created by plusend on 2016/12/12.
 */

public class Notification {
  @SerializedName("id") private int id;
  @SerializedName("type") private String type;
  @SerializedName("read") private boolean read;
  @SerializedName("actor") private Actor actor;
  @SerializedName("mention_type") private String mentionType;
  @SerializedName("mention") private Mention mention;
  @SerializedName("topic") private Object topic;
  @SerializedName("reply") private Reply reply;
  @SerializedName("node") private Object node;
  @SerializedName("created_at") private String createdAt;
  @SerializedName("updated_at") private String updatedAt;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public boolean isRead() {
    return read;
  }

  public void setRead(boolean read) {
    this.read = read;
  }

  public Actor getActor() {
    return actor;
  }

  public void setActor(Actor actor) {
    this.actor = actor;
  }

  public String getMentionType() {
    return mentionType;
  }

  public void setMentionType(String mentionType) {
    this.mentionType = mentionType;
  }

  public Mention getMention() {
    return mention;
  }

  public void setMention(Mention mention) {
    this.mention = mention;
  }

  public Object getTopic() {
    return topic;
  }

  public void setTopic(Object topic) {
    this.topic = topic;
  }

  public Reply getReply() {
    return reply;
  }

  public void setReply(Reply reply) {
    this.reply = reply;
  }

  public Object getNode() {
    return node;
  }

  public void setNode(Object node) {
    this.node = node;
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

  public static class Actor {
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

  public static class Mention {
    @SerializedName("id") private int id;
    @SerializedName("body_html") private String bodyHtml;
    @SerializedName("created_at") private String createdAt;
    @SerializedName("updated_at") private String updatedAt;
    @SerializedName("deleted") private boolean deleted;
    @SerializedName("topic_id") private int topicId;
    @SerializedName("user") private UserDetailInfo userDetailInfo;
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

    public UserDetailInfo getUserDetailInfo() {
      return userDetailInfo;
    }

    public void setUserDetailInfo(UserDetailInfo userDetailInfo) {
      this.userDetailInfo = userDetailInfo;
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
  }
  public static class Reply {
    @SerializedName("id") private int id;
    @SerializedName("body_html") private String bodyHtml;
    @SerializedName("created_at") private String createdAt;
    @SerializedName("updated_at") private String updatedAt;
    @SerializedName("deleted") private boolean deleted;
    @SerializedName("topic_id") private int topicId;
    @SerializedName("user") private User user;
    @SerializedName("likes_count") private int likesCount;
    @SerializedName("abilities") private Abilities abilities;
    @SerializedName("body") private String body;
    @SerializedName("topic_title") private String topicTitle;

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

    public String getBody() {
      return body;
    }

    public void setBody(String body) {
      this.body = body;
    }

    public String getTopicTitle() {
      return topicTitle;
    }

    public void setTopicTitle(String topicTitle) {
      this.topicTitle = topicTitle;
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
