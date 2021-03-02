package com.sonia.java.bankcheckapplication.model.bank.category;

import com.sonia.java.bankcheckapplication.model.user.CardCheckingUser;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "user_category_limit")
public class UserCategoryLimit {

    @EmbeddedId
    private UserCategoryLimitId id;

    @ManyToOne
    @MapsId("userId")
    private CardCheckingUser user;

    @ManyToOne
    @MapsId("category_id")
    private Category category;

    @Column(name = "limit_amount")
    private int limit;


    public UserCategoryLimit(CardCheckingUser user, Category category, int limit) {
        this.user = user;
        this.category = category;
        this.limit = limit;
    }

    public UserCategoryLimit() {
    }

    public void setId(UserCategoryLimitId id) {
        this.id = id;
    }

    public UserCategoryLimitId getId() {
        return id;
    }

    public CardCheckingUser getUser() {
        return user;
    }

    public void setUser(CardCheckingUser user) {
        this.user = user;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserCategoryLimit that = (UserCategoryLimit) o;
        return user.equals(that.user) && category.equals(that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, category);
    }

    @Override
    public String toString() {
        return "UserCategoryLimit{" +
                "id=" + id +
                ", user=" + user +
                ", category=" + category +
                ", limit=" + limit +
                '}';
    }
}
