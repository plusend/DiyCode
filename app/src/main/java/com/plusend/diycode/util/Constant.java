package com.plusend.diycode.util;

public class Constant {

    public static final String KEYSTORE_KEY_ALIAS = "DiyCode";
    public static final String VALUE_CLIENT_ID = "c2b56685";
    public static final String VALUE_CLIENT_SECRET =
        "869e0541ea80b2c48eea4a5c710d79448f2f9133abfbe203995801a8bc644068";
    public static final String VALUE_GRANT_TYPE_PASSWORD = "password";
    public static final String VALUE_GRANT_TYPE_REFRESH_TOKEN = "refresh_token";
    public static final String KEY_TOKEN = "Authorization";
    public static final String VALUE_TOKEN_PREFIX = "Bearer ";

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
}
