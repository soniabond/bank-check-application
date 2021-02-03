package com.sonia.java.bankcheckapplication.service;

import com.sonia.java.bankcheckapplication.model.bank.dataReciever.BankDataReceiver;
import com.sonia.java.bankcheckapplication.model.bank.factory.BankFactory;
import com.sonia.java.bankcheckapplication.model.bank.merchant.BankMerchantEntity;
import com.sonia.java.bankcheckapplication.model.bank.merchant.MonoBankMerchantEntity;
import com.sonia.java.bankcheckapplication.model.bank.merchant.PrivatBankMerchantEntity;
import com.sonia.java.bankcheckapplication.model.bank.req.balance.BalanceRequestData;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BankApiService {


    public void getMerchantTotalBalance(){
        List<BankMerchantEntity> bankMerchantEntities = new ArrayList<>();
        bankMerchantEntities.add(new MonoBankMerchantEntity());
        bankMerchantEntities.add(new PrivatBankMerchantEntity());
        float balance = 0;

        for (BankMerchantEntity merchant: bankMerchantEntities) {
            /*BankFactory bankFactory = merchant.getBank().getBankFactory();
            BalanceRequestData balanceRequestData = bankFactory.getBalanceRequestData();
            balanceRequestData.nestMerchant(merchant);
            BankDataReceiver dataReceiver = bankFactory.getDataReceiver();
            String json = dataReceiver.receiveBalance(balanceRequestData);
            balance += bankFactory.getParser().parseBalance(json);*/
        }

        System.out.println(balance);
    }



}
