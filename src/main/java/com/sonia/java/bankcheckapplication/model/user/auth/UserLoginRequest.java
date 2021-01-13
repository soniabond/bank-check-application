package com.sonia.java.bankcheckapplication.model.user.auth;

import com.fasterxml.jackson.annotation.JsonAlias;

public class UserLoginRequest {

    @JsonAlias({"email"})
    private String email;

    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
