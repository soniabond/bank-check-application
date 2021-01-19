package com.sonia.java.bankcheckapplication.model.user;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

public class ChangeUserPasswordRequest {

    @NotNull
    private String oldPassword;

    @NotBlank(message = "password must not be blank")
    @Size(min = 8, message = "password's length must be at least 8")
    private String newPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChangeUserPasswordRequest that = (ChangeUserPasswordRequest) o;
        return Objects.equals(oldPassword, that.oldPassword) &&
                Objects.equals(newPassword, that.newPassword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(oldPassword, newPassword);
    }

}
