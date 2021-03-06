package com.sonia.java.bankcheckapplication.model.bank;

import com.sonia.java.bankcheckapplication.model.bank.factory.BankFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class PrivatBank extends Bank{

    public PrivatBank(@Qualifier("privatBankFactory") BankFactory bankFactory) {
        this.bankFactory = bankFactory;
    }
}
