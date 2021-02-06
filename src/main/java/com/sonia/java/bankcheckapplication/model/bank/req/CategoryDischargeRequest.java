package com.sonia.java.bankcheckapplication.model.bank.req;


import java.util.List;

public class CategoryDischargeRequest {

    String category;

    List<String> discharge;

    public CategoryDischargeRequest(String category, List<String> discharge) {
        this.category = category;
        this.discharge = discharge;
    }

    public CategoryDischargeRequest(String category) {
        this.category = category;
    }

    public CategoryDischargeRequest() {
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<String> getDischarge() {
        return discharge;
    }

    public void setDischarge(List<String> discharge) {
        this.discharge = discharge;
    }
}
