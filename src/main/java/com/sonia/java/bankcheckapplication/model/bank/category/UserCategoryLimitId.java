package com.sonia.java.bankcheckapplication.model.bank.category;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserCategoryLimitId implements Serializable {


    @Column(name = "user_id", nullable = false)
    private long userId;

    @Column(name = "category_id", nullable = false)
    private int categoryId;

    public UserCategoryLimitId(long userId, int categoryId) {
        this.userId = userId;
        this.categoryId = categoryId;
    }

    public UserCategoryLimitId() {

    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserCategoryLimitId that = (UserCategoryLimitId) o;
        return userId == that.userId && categoryId == that.categoryId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, categoryId);
    }

    @Override
    public String toString() {
        return "UserCategoryLimitId{" +
                "userId=" + userId +
                ", categoryId=" + categoryId +
                '}';
    }
}
