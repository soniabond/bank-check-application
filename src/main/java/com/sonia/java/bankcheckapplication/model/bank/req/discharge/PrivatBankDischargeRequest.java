package com.sonia.java.bankcheckapplication.model.bank.req.discharge;

import com.sonia.java.bankcheckapplication.config.Config;
import com.sonia.java.bankcheckapplication.model.bank.merchant.BankMerchantEntity;
import com.sonia.java.bankcheckapplication.model.bank.merchant.PrivatBankMerchantEntity;
import com.sonia.java.bankcheckapplication.util.HashCoding;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Objects;

@Component
@Scope("prototype")
public class PrivatBankDischargeRequest extends DischargeRequestData {

    private HttpHeaders headers = new HttpHeaders();

    private final HttpMethod httpMethod = HttpMethod.POST;

    private final String url = "https://api.privatbank.ua/p24api/rest_fiz";

    private String startPeriod;

    private String endPeriod;

    private String merchantId;

    private String merchantSignature;

    private String cardNumber;


    private PrivatBankDischargeRequest() {
        headers.setContentType(MediaType.APPLICATION_XML);
    }

    @Override
    public DischargeRequestData nestMerchant (BankMerchantEntity bankMerchantEntity){
        PrivatBankMerchantEntity privatBankMerchant = (PrivatBankMerchantEntity) bankMerchantEntity;
        this.merchantId = privatBankMerchant.getMerchantId();
        this.merchantSignature = privatBankMerchant.getMerchantSignature();
        this.cardNumber = privatBankMerchant.getCardNumber();
        return this;
    }



    public HttpHeaders getHeaders() {
        return headers;
    }

    public void setHeaders(HttpHeaders headers) {
        this.headers = headers;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public String getUrl() {
        return url;
    }

    public String getStartPeriod() {

        return startPeriod;
    }

    private void setStartPeriod(Integer day, Integer month, int year) {
        String day1 = day.toString();
        if (day<10)
            day1 = "0"+day1;
        String month1 = month.toString();
        if (month<10)
            month1 = "0"+month1;
        this.startPeriod = day1+"."+month1+"."+year;
    }


    private void setEndPeriod(Integer day, Integer month, int year) {
        String day1 = day.toString();
        if (day<10)
            day1 = "0"+day1;
        String month1 = month.toString();
        if (month<10)
            month1 = "0"+month1;
        this.endPeriod = day1+"."+month1+"."+year;
    }


    private String getSignature(){
        String signature = "<oper>cmt</oper>" +
                "<wait>0</wait>" +
                "<test>0</test>" +
                "<payment id=\"\">\n" +
                "                        <prop name=\"sd\" value=\""+this.startPeriod+"\" />\n" +
                "                        <prop name=\"ed\" value=\""+this.endPeriod+"\" />\n" +
                "                        <prop name=\"card\" value=\""+this.cardNumber+"\" />\n" +
                "                    </payment>" +this.merchantSignature;
        signature = HashCoding.hashSignatureForPrivatBank(signature);
        return signature;
    }

    public String formRequestBody(){
        return  "<?xml version='1.0' encoding='UTF-8'?><request version=\"1.0\"><merchant><id>"+this.merchantId+"</id><signature>" + getSignature() + "</signature></merchant><data>" +
                "<oper>cmt</oper>" +
                "<wait>0</wait>" +
                "<test>0</test>" +
                "<payment id=\"\">\n" +
                "                        <prop name=\"sd\" value=\""+this.startPeriod+"\" />\n" +
                "                        <prop name=\"ed\" value=\""+this.endPeriod+"\" />\n" +
                "                        <prop name=\"card\" value=\""+this.cardNumber+"\" />\n" +
                "                    </payment>" +
                "</data></request>";
    }

    @Override
    public DischargeRequestData setPeriod(int month, int year) {

        int endDay;
        int startDay = 1;
        LocalDate localDate = LocalDate.now(Config.zoneId);
        if(month == localDate.getMonthValue() && year == localDate.getYear()){
            endDay = localDate.getDayOfMonth();
        }
        else {
            endDay = LocalDate.of(year, month, 1)
                    .plusMonths(1)
                    .minusDays(1)
                    .getDayOfMonth();
        }
        this.setStartPeriod(startDay, month, year);
        this.setEndPeriod(endDay, month, year);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrivatBankDischargeRequest that = (PrivatBankDischargeRequest) o;
        return Objects.equals(startPeriod, that.startPeriod) && Objects.equals(endPeriod, that.endPeriod) && Objects.equals(merchantId, that.merchantId) && Objects.equals(merchantSignature, that.merchantSignature) && Objects.equals(cardNumber, that.cardNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startPeriod, endPeriod, merchantId, merchantSignature, cardNumber);
    }

    @Override
    public String toString() {
        return "PrivatBankDischargeRequest{" +
                "url='" + url + '\'' +
                ", merchantId='" + merchantId + '\'' +
                ", merchantSignature='" + merchantSignature + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                '}' + this.formRequestBody();
    }
}
