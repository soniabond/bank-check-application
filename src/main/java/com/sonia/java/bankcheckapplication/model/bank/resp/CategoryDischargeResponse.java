package com.sonia.java.bankcheckapplication.model.bank.resp;

import com.sonia.java.bankcheckapplication.model.bank.category.Category;
import com.sonia.java.bankcheckapplication.model.bank.discharge.BankDischarge;

import java.io.Serializable;
import java.util.List;

public class CategoryDischargeResponse implements Serializable {

    private String categoryName;

    private BankDischarge discharge;

    public CategoryDischargeResponse(String categoryName, BankDischarge discharge) {
        this.categoryName = categoryName;
        this.discharge = discharge;
    }

    public CategoryDischargeResponse() {

    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public BankDischarge getDischarge() {
        return discharge;
    }

    public void setDischarge(BankDischarge discharge) {
        this.discharge = discharge;
    }




}