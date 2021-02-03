package com.sonia.java.bankcheckapplication.model.bank.factory;

import com.sonia.java.bankcheckapplication.model.bank.dataReciever.BankDataReceiver;
import com.sonia.java.bankcheckapplication.model.bank.req.balance.BalanceRequestData;
import com.sonia.java.bankcheckapplication.model.bank.req.discharge.DischargeRequestData;
import com.sonia.java.bankcheckapplication.service.parser.ResponseParser;

public abstract class BankFactory {

    protected ResponseParser parser;

    protected BankDataReceiver dataReceiver;

    protected DischargeRequestData dischargeRequestData;

    protected BalanceRequestData balanceRequestData;

    public abstract DischargeRequestData getRequestData();
    public abstract ResponseParser getParser();
    public abstract BankDataReceiver getDataReceiver();
    public abstract BalanceRequestData getBalanceRequestData();
}
