package com.sonia.java.bankcheckapplication.model.user.req;

public class ChangeCategoryLimitRequest {

    private String categoryName;

    private int newLimit;

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
