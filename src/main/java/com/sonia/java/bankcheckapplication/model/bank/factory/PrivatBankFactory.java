package com.sonia.java.bankcheckapplication.model.bank.factory;

import com.sonia.java.bankcheckapplication.model.bank.dataReciever.BankDataReceiver;
import com.sonia.java.bankcheckapplication.model.bank.req.balance.BalanceRequestData;
import com.sonia.java.bankcheckapplication.model.bank.req.discharge.DischargeRequestData;
import com.sonia.java.bankcheckapplication.service.parser.ResponseParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class PrivatBankFactory extends BankFactory{


    @Autowired
    public PrivatBankFactory(@Qualifier("privatBankResponseParser") ResponseParser parser,
                             @Qualifier("privatBankWebApiDataReceiver") BankDataReceiver dataReceiver,
                             @Qualifier("privatBankDischargeRequest") DischargeRequestData dischargeRequestData,
                             @Qualifier("privatBankBalanceRequestData") BalanceRequestData balanceRequestData) {
        this.parser = parser;
        this.dataReceiver = dataReceiver;
        this.dischargeRequestData = dischargeRequestData;
        this.balanceRequestData = balanceRequestData;
    }

    @Override
    public DischargeRequestData getRequestData() {
        return this.dischargeRequestData;
    }

    @Override
    public ResponseParser getParser() {
        return this.parser;
    }

    @Override
    public BankDataReceiver getDataReceiver() {
        return this.dataReceiver;
    }

    @Override
    public BalanceRequestData getBalanceRequestData() {
        return this.balanceRequestData;
    }

}
