package com.sonia.java.bankcheckapplication.util;

import com.sonia.java.bankcheckapplication.ApplicationContextProvider;
import com.sonia.java.bankcheckapplication.exceptions.BankApiExceptions;
import com.sonia.java.bankcheckapplication.model.bank.MonoBank;
import com.sonia.java.bankcheckapplication.model.bank.factory.BankFactory;
import com.sonia.java.bankcheckapplication.model.bank.merchant.BankMerchantEntity;
import com.sonia.java.bankcheckapplication.model.bank.merchant.MonoBankMerchantEntity;
import com.sonia.java.bankcheckapplication.model.bank.merchant.PrivatBankMerchantEntity;
import com.sonia.java.bankcheckapplication.model.bank.req.balance.BalanceRequestData;
import com.sonia.java.bankcheckapplication.service.parser.MonoBankResponseParser;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

public class MerchantDataValidation {

    public static void validate(BankMerchantEntity  bankMerchant) throws ResponseStatusException {
        BankFactory bankFactory = bankMerchant.getBank().getBankFactory();
        BalanceRequestData balanceRequestData =
                bankFactory.getBalanceRequestData().nestMerchant(bankMerchant);
        try{
        String resp = bankFactory.getDataReceiver().receiveBalance(balanceRequestData);
        bankFactory.getParser().parseBalance(resp);
        }catch (HttpClientErrorException e){
            throw BankApiExceptions.invalidXTokenException();
        }

    }



}
