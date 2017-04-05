package com.plusend.diycode.model.user.entity;

import com.google.gson.annotations.SerializedName;

public class UserDetailInfo {
    @SerializedName("id") private int id;
    @SerializedName("login") private String login;
    @SerializedName("name") private String name;
    @SerializedName("avatar_url") private String avatarUrl;
    @SerializedName("location") private Object location;
    @SerializedName("company") private Object company;
    @SerializedName("twitter") private Object twitter;
    @SerializedName("website") private Object website;
    @SerializedName("bio") private Object bio;
    @SerializedName("tagline") private Object tagline;
    @SerializedName("github") private String github;
    @SerializedName("created_at") private String createdAt;
    @SerializedName("email") private String email;
    @SerializedName("topics_count") private int topicsCount;
    @SerializedName("replies_count") private int repliesCount;
    @SerializedName("following_count") private int followingCount;
    @SerializedName("followers_count") private int followersCount;
    @SerializedName("favorites_count") private int favoritesCount;
    @SerializedName("level") private String level;
    @SerializedName("level_name") private String levelName;

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

    public Object getLocation() {
        return location;
    }

    public void setLocation(Object location) {
        this.location = location;
    }

    public Object getCompany() {
        return company;
    }

    public void setCompany(Object company) {
        this.company = company;
    }

    public Object getTwitter() {
        return twitter;
    }

    public void setTwitter(Object twitter) {
        this.twitter = twitter;
    }

    public Object getWebsite() {
        return website;
    }

    public void setWebsite(Object website) {
        this.website = website;
    }

    public Object getBio() {
        return bio;
    }

    public void setBio(Object bio) {
        this.bio = bio;
    }

    public Object getTagline() {
        return tagline;
    }

    public void setTagline(Object tagline) {
        this.tagline = tagline;
    }

    public String getGithub() {
        return github;
    }

    public void setGithub(String github) {
        this.github = github;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTopicsCount() {
        return topicsCount;
    }

    public void setTopicsCount(int topicsCount) {
        this.topicsCount = topicsCount;
    }

    public int getRepliesCount() {
        return repliesCount;
    }

    public void setRepliesCount(int repliesCount) {
        this.repliesCount = repliesCount;
    }

    public int getFollowingCount() {
        return followingCount;
    }

    public void setFollowingCount(int followingCount) {
        this.followingCount = followingCount;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(int followersCount) {
        this.followersCount = followersCount;
    }

    public int getFavoritesCount() {
        return favoritesCount;
    }

    public void setFavoritesCount(int favoritesCount) {
        this.favoritesCount = favoritesCount;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
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
            + ", location="
            + location
            + ", company="
            + company
            + ", twitter="
            + twitter
            + ", website="
            + website
            + ", bio="
            + bio
            + ", tagline="
            + tagline
            + ", github='"
            + github
            + '\''
            + ", createdAt='"
            + createdAt
            + '\''
            + ", email='"
            + email
            + '\''
            + ", topicsCount="
            + topicsCount
            + ", repliesCount="
            + repliesCount
            + ", followingCount="
            + followingCount
            + ", followersCount="
            + followersCount
            + ", favoritesCount="
            + favoritesCount
            + ", level='"
            + level
            + '\''
            + ", levelName='"
            + levelName
            + '\''
            + '}';
    }
}
