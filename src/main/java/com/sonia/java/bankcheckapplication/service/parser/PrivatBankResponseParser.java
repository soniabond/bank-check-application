package com.sonia.java.bankcheckapplication.service.parser;

import com.sonia.java.bankcheckapplication.exceptions.BankApiExceptions;
import com.sonia.java.bankcheckapplication.model.bank.discharge.BankDischarge;
import com.sonia.java.bankcheckapplication.model.bank.discharge.PrivatBankDischarge;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
@Scope("prototype")
public class PrivatBankResponseParser implements ResponseParser {


    @Override
    public List<BankDischarge> parseDischarge(String xml) {
        System.out.println(xml);
        try {
            validateResponse(xml);
        }catch (ResponseStatusException e){
            if(Objects.equals(e.getMessage(), "invalid merchant data")){
                return new ArrayList<>();
            }
        }
        List<String> xmls = new ArrayList<>(Arrays.asList(xml.split("\n")));

        xmls.remove(0);
        xmls.remove(xmls.size()-1);
        try {
            return this.XmlStringToObject(xmls);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public float parseBalance(String parsableResponse) {
        try {
            validateResponse(parsableResponse);
        }catch (ResponseStatusException e){
            if(Objects.equals(e.getMessage(), "invalid merchant data")){
                return 0;
            }
        }
        String balance = parsableResponse.split("<balance>")[1].split("<")[0];
        return Float.parseFloat(balance);
    }

    private List<BankDischarge> XmlStringToObject(List<String> xmls) throws ParseException {
        List<BankDischarge> privatBankDischarges = new ArrayList<>();
        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        for(String xmlString: xmls) {
            List<String> keyValue = new ArrayList<>(Arrays.asList(xmlString.split("=")));
            keyValue.remove(0);

            List<String> fields = new ArrayList<>();

            for (String item : keyValue) {
                fields.add(item.split("\"")[1]);
            }

            PrivatBankDischarge dischargeResponse = new PrivatBankDischarge();
            dischargeResponse.setTrandate(simpleDateFormat.parse(fields.get(2)+' '+fields.get(3)));
            dischargeResponse.setCardamount(Float.parseFloat(fields.get(5).split(" ")[0]));
            dischargeResponse.setTerminal(fields.get(7));
            dischargeResponse.setDescription(fields.get(8));


            privatBankDischarges.add(dischargeResponse);

        }

        return privatBankDischarges;
    }

    private void validateResponse(String response){
        List<String> errorMarkers = new ArrayList<>();
        errorMarkers.add("invalid merchant id");
        errorMarkers.add("this card is not in merchants card");
        for (String errorMarker: errorMarkers){
            if (response.contains(errorMarker)){
                throw BankApiExceptions.invalidPrivatData(errorMarker);
            }
        }
        if (response.contains("error message") || response.contains("error")){
            throw BankApiExceptions.invalidPrivatData("invalid merchant data");
        }
    }

}

