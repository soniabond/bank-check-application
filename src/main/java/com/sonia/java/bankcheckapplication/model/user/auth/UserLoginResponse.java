package com.sonia.java.bankcheckapplication.model.user.auth;

public class UserLoginResponse {

    private String accessToken;

    private long expireIn;

    public UserLoginResponse() {
    }

    public UserLoginResponse(String accessToken, long expireIn) {
        this.accessToken = accessToken;
        this.expireIn = expireIn;
    }

    public String getTokenType() {
        return "bearer";
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public long getExpireIn() {
        return expireIn;
    }

    public void setExpireIn(long expireIn) {
        this.expireIn = expireIn;
    }
}

