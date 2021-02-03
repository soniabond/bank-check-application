package com.sonia.java.bankcheckapplication.model.bank.req.balance;

import com.sonia.java.bankcheckapplication.model.bank.merchant.BankMerchantEntity;

public abstract class BalanceRequestData {

    public abstract BalanceRequestData nestMerchant(BankMerchantEntity merchant);
}
