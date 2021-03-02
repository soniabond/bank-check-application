package com.sonia.java.bankcheckapplication.model.user;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.Instant;
import java.util.EnumSet;
import java.util.Set;

public class UserResponse {

    private long id;

    private String email;

    private String firstName;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Instant createdAt;

    private Set<KnownAuthority> authorities;

    public static UserResponse fromUser(CardCheckingUser user){
        var response = new UserResponse();
        response.id = user.getId();
        response.email = user.getEmail();
        response.firstName = user.getFirstName();
        response.createdAt = user.getCreatedAt();
        response.authorities = EnumSet.copyOf(user.getAuthorities().keySet());
        return response;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Set<KnownAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<KnownAuthority> authorities) {
        this.authorities = authorities;
    }
}
