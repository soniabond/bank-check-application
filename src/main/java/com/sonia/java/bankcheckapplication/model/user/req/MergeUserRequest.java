package com.sonia.java.bankcheckapplication.model.user.req;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.Objects;

public class MergeUserRequest {

    @Email(message = "email must be a valid email string")
    private String email;

    @Size(min = 8, message = "password's length must be at least 8")
    private String password;

    private String firstName;

    public MergeUserRequest() {
    }

    public MergeUserRequest(String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.firstName = nickname;
    }

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

    public String getNickname() {
        return firstName;
    }

    public void setNickname(String nickname) {
        this.firstName = nickname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MergeUserRequest that = (MergeUserRequest) o;
        return Objects.equals(email, that.email) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, firstName, password);
    }

    @Override
    public String toString() {
        return "MergeUserRequest{" +
                "email='" + email + '\'' +
                ", nickname='" + firstName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}