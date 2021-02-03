package com.sonia.java.bankcheckapplication.model.bank.resp.balanceApiResp;

import java.util.List;

public class Account {
    private String id;
    private Integer currencyCode;
    private String cashbackType;
    private Integer balance;
    private Integer creditLimit;
    private List<String> maskedPan;
    private String type;
    private String iban;

    @Override
    public String toString() {
        return "Account{" +
                "id='" + id + '\'' +
                ", currencyCode=" + currencyCode +
                ", cashbackType='" + cashbackType + '\'' +
                ", balance=" + balance +
                ", creditLimit=" + creditLimit +
                ", maskedPan=" + maskedPan +
                ", type='" + type + '\'' +
                ", iban='" + iban + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(Integer currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCashbackType() {
        return cashbackType;
    }

    public void setCashbackType(String cashbackType) {
        this.cashbackType = cashbackType;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public Integer getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(Integer creditLimit) {
        this.creditLimit = creditLimit;
    }

    public List<String> getMaskedPan() {
        return maskedPan;
    }

    public void setMaskedPan(List<String> maskedPan) {
        this.maskedPan = maskedPan;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }
}

