package com.sonia.java.bankcheckapplication.model.bank.req.discharge;

import com.sonia.java.bankcheckapplication.model.bank.merchant.BankMerchantEntity;

public abstract class DischargeRequestData {
    public abstract DischargeRequestData setPeriod(int month, int year);
    public abstract DischargeRequestData nestMerchant(BankMerchantEntity merchant);
}
