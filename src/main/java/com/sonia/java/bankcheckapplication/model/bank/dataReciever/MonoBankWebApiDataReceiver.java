package com.sonia.java.bankcheckapplication.model.bank.dataReciever;

import com.sonia.java.bankcheckapplication.exceptions.BankApiExceptions;
import com.sonia.java.bankcheckapplication.model.bank.req.balance.BalanceRequestData;
import com.sonia.java.bankcheckapplication.model.bank.req.balance.MonoBankBalanceRequestData;
import com.sonia.java.bankcheckapplication.model.bank.req.discharge.DischargeRequestData;
import com.sonia.java.bankcheckapplication.model.bank.req.discharge.MonoBankDischargeRequest;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@Component
@Scope("prototype")
public class MonoBankWebApiDataReceiver extends BankDataReceiver{


    @Override
    public String receiveDischarge(DischargeRequestData dischargeRequestData) throws ResponseStatusException {
        try {
            MonoBankDischargeRequest monoBankDischargeRequest = (MonoBankDischargeRequest) dischargeRequestData;
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();

            for (Map.Entry<String, String> entry : monoBankDischargeRequest.getHeaders().entrySet()) {
                headers.add(entry.getKey(), entry.getValue());
            }
            HttpEntity request = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(monoBankDischargeRequest.toString(), monoBankDischargeRequest.getHttpMethod(), request, String.class);
            this.discharge = response.toString();
            return (String) discharge;
        }catch (HttpClientErrorException e){
            throw BankApiExceptions.invalidXTokenException();
        }

    }

    @Override
    public String receiveBalance(BalanceRequestData balanceRequestData) {
        try {
            MonoBankBalanceRequestData monoBankBalanceRequestData = (MonoBankBalanceRequestData) balanceRequestData;
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();

            for (Map.Entry<String, String> entry : monoBankBalanceRequestData.getHeaders().entrySet()) {
                headers.add(entry.getKey(), entry.getValue());
            }
            HttpEntity request = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(monoBankBalanceRequestData.toString(),
                    monoBankBalanceRequestData.getHttpMethod(), request, String.class);
            this.discharge = response.toString();
            return (String) discharge;
        }catch (HttpClientErrorException e){
            throw BankApiExceptions.invalidXTokenException();
        }
    }
}
