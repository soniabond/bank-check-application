package com.sonia.java.bankcheckapplication.service.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sonia.java.bankcheckapplication.exceptions.BankApiExceptions;
import com.sonia.java.bankcheckapplication.model.bank.balance.MonoBankBalance;
import com.sonia.java.bankcheckapplication.model.bank.discharge.BankDischarge;
import com.sonia.java.bankcheckapplication.model.bank.discharge.MonoBankDischarge;
import com.sonia.java.bankcheckapplication.model.bank.resp.MonoBankDischargeResponse;
import com.sonia.java.bankcheckapplication.model.bank.resp.balanceApiResp.MonoBankBalanceWebApiResponseJson;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Scope("prototype")
public class MonoBankResponseParser implements ResponseParser {


    @Override
    public List<BankDischarge> parseDischarge(String json) throws ResponseStatusException{ ;
        json = json.split("\\[")[1];
        json = '['+json;
        ObjectMapper mapper = new ObjectMapper();

        try {
            List<MonoBankDischargeResponse> jsons = Arrays.asList(mapper.readValue(json, MonoBankDischargeResponse[].class));
            List<BankDischarge> bankDischarges = jsons.stream()
                    .map(MonoBankDischarge::new)
                    .collect(Collectors.toList());
            bankDischarges.forEach(System.out::println);
            return bankDischarges;


        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    @Override
    public float parseBalance(String parsableJson) throws ResponseStatusException {

        String json = parsableJson.split("Date")[0];
        json = json.substring(5, json.length()-2);
        System.out.println(json);
        ObjectMapper mapper = new ObjectMapper();
        MonoBankBalanceWebApiResponseJson response;

        try {
            response = mapper.readValue(json, MonoBankBalanceWebApiResponseJson.class);
            MonoBankBalance monoBankBalance = MonoBankBalance.fromMonoBankBalanceWebApiResponseJson(response);
            System.out.println(monoBankBalance);
            return monoBankBalance.getBalance();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
