package com.sonia.java.bankcheckapplication.model.bank.resp;

import com.sonia.java.bankcheckapplication.model.bank.category.Category;
import com.sonia.java.bankcheckapplication.model.bank.discharge.BankDischarge;

import java.io.Serializable;
import java.util.List;

public class CategoryDischargeResponse implements Serializable {
    String categoryName;
    List<BankDischarge> discharges;
    Float amount = (float)0;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public CategoryDischargeResponse(String categoryName, List<BankDischarge> discharges, Float amount) {
        this.categoryName = categoryName;
        this.discharges = discharges;
        this.amount = amount;
    }

    public List<BankDischarge> getDischarges() {
        return discharges;
    }

    public void setDischarges(List<BankDischarge> discharges) {
        this.discharges = discharges;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public CategoryDischargeResponse() {
    }

    @Override
    public String toString() {
        return "CategoryDischargeResponse{" +
                "category=" + categoryName +
                ", amount=" + amount +
                ", discharges=" + discharges +
                '}';
    }
}