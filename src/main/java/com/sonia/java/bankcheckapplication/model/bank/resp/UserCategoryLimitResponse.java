package com.sonia.java.bankcheckapplication.model.bank.resp;

import java.util.Objects;

public class UserCategoryLimitResponse {

    String categoryName;

    int limit;

    public UserCategoryLimitResponse(String categoryName, int limit) {
        this.categoryName = categoryName;
        this.limit = limit;
    }

    public UserCategoryLimitResponse() {
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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
        UserCategoryLimitResponse response = (UserCategoryLimitResponse) o;
        return categoryName.equals(response.categoryName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryName);
    }

    @Override
    public String toString() {
        return "UserCategoryLimitResponse{" +
                "categoryName='" + categoryName + '\'' +
                ", limit=" + limit +
                '}';
    }
}
