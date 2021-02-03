package com.sonia.java.bankcheckapplication.model.bank;

import com.sonia.java.bankcheckapplication.model.bank.factory.BankFactory;

public abstract class Bank {

    protected BankFactory bankFactory;


    public BankFactory getBankFactory() {
        return bankFactory;
    }

    public void setBankFactory(BankFactory bankFactory) {
        this.bankFactory = bankFactory;
    }


}