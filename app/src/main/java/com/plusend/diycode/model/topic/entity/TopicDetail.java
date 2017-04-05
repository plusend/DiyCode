package com.plusend.diycode.model.topic.entity;

import com.google.gson.annotations.SerializedName;

public class TopicDetail {
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
    @SerializedName("body") private String body;
    @SerializedName("body_html") private String bodyHtml;
    @SerializedName("hits") private int hits;
    @SerializedName("likes_count") private int likesCount;
    @SerializedName("suggested_at") private String suggestedAt;
    @SerializedName("followed") private boolean followed;
    @SerializedName("liked") private boolean liked;
    @SerializedName("favorited") private boolean favorited;

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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getBodyHtml() {
        return bodyHtml;
    }

    public void setBodyHtml(String bodyHtml) {
        this.bodyHtml = bodyHtml;
    }

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public String getSuggestedAt() {
        return suggestedAt;
    }

    public void setSuggestedAt(String suggestedAt) {
        this.suggestedAt = suggestedAt;
    }

    public boolean isFollowed() {
        return followed;
    }

    public void setFollowed(boolean followed) {
        this.followed = followed;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public boolean isFavorited() {
        return favorited;
    }

    public void setFavorited(boolean favorited) {
        this.favorited = favorited;
    }

    @Override public String toString() {
        return "TopicDetail{"
            + "id="
            + id
            + ", title='"
            + title
            + '\''
            + ", createdAt='"
            + createdAt
            + '\''
            + ", updatedAt='"
            + updatedAt
            + '\''
            + ", repliedAt='"
            + repliedAt
            + '\''
            + ", repliesCount="
            + repliesCount
            + ", nodeName='"
            + nodeName
            + '\''
            + ", nodeId="
            + nodeId
            + ", lastReplyUserId="
            + lastReplyUserId
            + ", lastReplyUserLogin='"
            + lastReplyUserLogin
            + '\''
            + ", user="
            + user
            + ", deleted="
            + deleted
            + ", excellent="
            + excellent
            + ", abilities="
            + abilities
            + ", body='"
            + body
            + '\''
            + ", bodyHtml='"
            + bodyHtml
            + '\''
            + ", hits="
            + hits
            + ", likesCount="
            + likesCount
            + ", suggestedAt='"
            + suggestedAt
            + '\''
            + ", followed="
            + followed
            + ", liked="
            + liked
            + ", favorited="
            + favorited
            + '}';
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
            return "User{"
                + "id="
                + id
                + ", login='"
                + login
                + '\''
                + ", name='"
                + name
                + '\''
                + ", avatarUrl='"
                + avatarUrl
                + '\''
                + '}';
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
            return "Abilities{" + "update=" + update + ", destroy=" + destroy + '}';
        }
    }
}
