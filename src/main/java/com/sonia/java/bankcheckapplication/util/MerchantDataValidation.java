package com.sonia.java.bankcheckapplication.util;

import com.sonia.java.bankcheckapplication.ApplicationContextProvider;
import com.sonia.java.bankcheckapplication.exceptions.BankApiExceptions;
import com.sonia.java.bankcheckapplication.model.bank.MonoBank;
import com.sonia.java.bankcheckapplication.model.bank.factory.BankFactory;
import com.sonia.java.bankcheckapplication.model.bank.merchant.MonoBankMerchantEntity;
import com.sonia.java.bankcheckapplication.model.bank.req.balance.BalanceRequestData;
import com.sonia.java.bankcheckapplication.service.parser.MonoBankResponseParser;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

public class MerchantDataValidation {

    public static void validateMonoData(MonoBankMerchantEntity monoBankMerchant) throws ResponseStatusException {
        BankFactory monoBankFactory = monoBankMerchant.getBank().getBankFactory();
        BalanceRequestData balanceRequestData =
                monoBankFactory.getBalanceRequestData().nestMerchant(monoBankMerchant);
        try{
        String json = monoBankFactory.getDataReceiver().receiveBalance(balanceRequestData);
        }catch (HttpClientErrorException e){
            throw BankApiExceptions.invalidXTokenException();
        }

    }
}
