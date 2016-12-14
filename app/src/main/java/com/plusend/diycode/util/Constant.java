package com.plusend.diycode.util;

/**
 * Created by plusend on 2016/11/24.
 */

public class Constant {
  public static final String HOST = "http://www.diycode.cc/api/v3";
  public static final String TOPICS = "/topics.json";
  public static final String TOPIC = "/topics/id.json";
  public static final String REPLIES = "/topics/id/replies.json";
  public static final String SITE = "/sites.json";
  public static final String ME = "/users/me.json";

  public static final String AUTH_TOKEN = "https://www.diycode.cc/oauth/token";
  public static final String KEY_CLIENT_ID = "client_id";
  public static final String VALUE_CLIENT_ID = "5e9268a6";
  public static final String KEY_CLIENT_SECRET = "client_secret";
  public static final String VALUE_CLIENT_SECRET =
      "1feed4136daa95b76e53236cb937b63995c725dd33cd6705639384b9b34ea130";
  public static final String KEY_GRANT_TYPE = "grant_type";
  public static final String VALUE_GRANT_TYPE = "password";
  public static final String KEY_TOKEN = "Authorization";
  public static final String VALUE_TOKEN_PREFIX = "Bearer ";
  public static final String KEY_USERNAME = "username";
  public static String VALUE_USERNAME = "";
  public static final String KEY_PASSWORD = "password";
  public static String VALUE_PASSWORD = "";

  public static String VALUE_TOKEN = "";

  public static class Token {
    public static final String SHARED_PREFERENCES_NAME = "sign";
    public static final String ACCESS_TOKEN = "access_token";
    public static final String TOKEN_TYPE = "token_type";
    public static final String EXPIRES_IN = "expires_in";
    public static final String REFRESH_TOKEN = "refresh_token";
    public static final String CREATED_AT = "created_at";
  }

  public static class User {
    public static final String LOGIN = "login";
    public static final String AVATAR_URL = "avatar_url";
    public static final String EMAIL = "email";
  }

  public static final String TOPIC_ID = "topicId";
  public static final String TOPIC_TITLE = "topicTitle";
}
