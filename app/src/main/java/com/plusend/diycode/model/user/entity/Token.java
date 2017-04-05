package com.plusend.diycode.model.user.entity;

import com.google.gson.annotations.SerializedName;

public class Token {
    @SerializedName("access_token") private String accessToken;
    @SerializedName("token_type") private String tokenType;
    // accessToken 有效期60天
    @SerializedName("expires_in") private int expiresIn;
    @SerializedName("refresh_token") private String refreshToken;
    // accessToken 创建时间距 1970-01-01 00:00:00 的秒数
    @SerializedName("created_at") private int createdAt;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public int getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(int createdAt) {
        this.createdAt = createdAt;
    }

    @Override public String toString() {
        return "Token{"
            + "accessToken='"
            + accessToken
            + '\''
            + ", tokenType='"
            + tokenType
            + '\''
            + ", expiresIn="
            + expiresIn
            + ", refreshToken='"
            + refreshToken
            + '\''
            + ", createdAt="
            + createdAt
            + '}';
    }
}
