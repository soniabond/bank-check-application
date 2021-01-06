package com.sonia.java.bankcheckapplication.model.user;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

public class UserResponse {

    private long id;

    private String email;

    private String firstName;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Instant createdAt;

    private Set<String> authorities;

    public static UserResponse fromUser(CardChekingUser user){
        UserResponse userResponse = new UserResponse();
        userResponse.id = user.getId();
        userResponse.firstName = user.getFirstName();
        userResponse.createdAt = user.getCreatedAt();
        userResponse.authorities = user.getAuthorities()
                .stream()
                .map(cardCheckingUserAuthority -> cardCheckingUserAuthority.getValue().name())
                .collect(Collectors.toSet());
        userResponse.email = user.getEmail();
        return userResponse;
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

    public Set<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
    }
}
