package com.sonia.java.bankcheckapplication.model.user.req;

public class ChangeCategoryLimitRequest {

    private String email;

    private String categoryName;

    private int newLimit;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getNewLimit() {
        return newLimit;
    }

    public void setNewLimit(int newLimit) {
        this.newLimit = newLimit;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
