package com.sonia.java.bankcheckapplication.model.bank.resp;

public class BalanceResponse {
    float balance;

    public BalanceResponse(float balance) {
        this.balance = balance;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }
}
