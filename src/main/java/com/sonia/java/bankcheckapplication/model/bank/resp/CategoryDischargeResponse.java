package com.sonia.java.bankcheckapplication.model.bank.resp;

import com.sonia.java.bankcheckapplication.model.bank.category.Category;
import com.sonia.java.bankcheckapplication.model.bank.discharge.BankDischarge;

import java.io.Serializable;
import java.util.List;

public class CategoryDischargeResponse implements Serializable {
    Category category;
    List<BankDischarge> discharges;
    Float amount = (float)0;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
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

    public CategoryDischargeResponse(Category category, List<BankDischarge> discharges, Float amount) {
        this.category = category;
        this.discharges = discharges;
        this.amount = amount;
    }
    public CategoryDischargeResponse() {
    }

    @Override
    public String toString() {
        return "CategoryDischargeResponse{" +
                "category=" + category +
                ", amount=" + amount +
                ", discharges=" + discharges +
                '}';
    }
}