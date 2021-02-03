package com.sonia.java.bankcheckapplication.model.bank.dataReciever;

import com.sonia.java.bankcheckapplication.model.bank.req.balance.BalanceRequestData;
import com.sonia.java.bankcheckapplication.model.bank.req.balance.PrivatBankBalanceRequestData;
import com.sonia.java.bankcheckapplication.model.bank.req.discharge.DischargeRequestData;
import com.sonia.java.bankcheckapplication.model.bank.req.discharge.PrivatBankDischargeRequest;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Scope("prototype")
public class PrivatBankWebApiDataReceiver extends BankDataReceiver{

    @Override
    public String receiveDischarge(DischargeRequestData dischargeRequestData) {
        PrivatBankDischargeRequest privatBankDischargeRequest = (PrivatBankDischargeRequest) dischargeRequestData;
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity request = new HttpEntity(privatBankDischargeRequest.formRequestBody(), privatBankDischargeRequest.getHeaders());
        ResponseEntity<String> response = restTemplate.postForEntity(privatBankDischargeRequest.getUrl(), request, String.class);
        this.discharge = response.toString();
        return (String) discharge;
    }

    @Override
    public String receiveBalance(BalanceRequestData balanceRequestData) {
        PrivatBankBalanceRequestData bankBalanceRequestData = (PrivatBankBalanceRequestData) balanceRequestData;
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity request = new HttpEntity(bankBalanceRequestData.formRequestBody());
        System.out.println(bankBalanceRequestData.getUrl());
        System.out.println(bankBalanceRequestData.formRequestBody());
        ResponseEntity<String> response = restTemplate.postForEntity(bankBalanceRequestData.getUrl(), request, String.class);
        System.out.println(response);
        return response.toString();
    }
}

