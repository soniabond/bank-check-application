package com.sonia.java.bankcheckapplication.model.bank;

import com.sonia.java.bankcheckapplication.model.bank.Bank;
import com.sonia.java.bankcheckapplication.model.bank.factory.BankFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class MonoBank extends Bank {

    public MonoBank(@Qualifier("monoBankFactory") BankFactory bankFactory) {
        this.bankFactory = bankFactory;
    }
}
