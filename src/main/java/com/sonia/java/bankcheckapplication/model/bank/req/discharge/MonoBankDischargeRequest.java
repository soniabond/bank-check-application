package com.sonia.java.bankcheckapplication.model.bank.req.discharge;

import com.sonia.java.bankcheckapplication.config.Config;
import com.sonia.java.bankcheckapplication.model.bank.merchant.BankMerchantEntity;
import com.sonia.java.bankcheckapplication.model.bank.merchant.MonoBankMerchantEntity;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
@Scope("prototype")
public class MonoBankDischargeRequest extends DischargeRequestData {

    private Map<String, String> headers = new HashMap<>();

    private final HttpMethod httpMethod = HttpMethod.GET;

    private final String url = "https://api.monobank.ua/personal/statement/0/";

    private Integer startPeriodSec;

    private Integer endPeriodSec;


    public MonoBankDischargeRequest(){
    }

    public Integer getStartPeriodSec() {
        return startPeriodSec;
    }

    private void setStartPeriodSec(Integer startPeriodSec) {
        this.startPeriodSec = startPeriodSec;
    }

    public Integer getEndPeriodSec() {
        return endPeriodSec;
    }

    private void setEndPeriodSec(Integer endPeriodSec) {
        this.endPeriodSec = endPeriodSec;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public String getUrl() {
        return url;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    @Override
    public DischargeRequestData nestMerchant(BankMerchantEntity merchant) {
        MonoBankMerchantEntity monoBankMerchant = (MonoBankMerchantEntity) merchant;
        this.headers.put("X-Token", monoBankMerchant.getxToken());
        return this;

    }

    @Override
    public String toString() {
        return url+startPeriodSec + "/" + endPeriodSec;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MonoBankDischargeRequest that = (MonoBankDischargeRequest) o;
        return startPeriodSec.equals(that.startPeriodSec) && endPeriodSec.equals(that.endPeriodSec);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startPeriodSec, endPeriodSec);
    }

    public DischargeRequestData setPeriod(int month, int year) {
        LocalDate localDate = LocalDate.now(Config.zoneId);
        LocalDate startDate;
        LocalDate endDate;
        int currDay = localDate.getDayOfMonth();
        if(month == localDate.getMonthValue() && year == localDate.getYear()){
            endDate = localDate;
            startDate = localDate.minusDays(currDay-1);
        }
        else {
            startDate = LocalDate.of(year, month,1);
            endDate = startDate.plusMonths(1).minusDays(1);
        }

        this.endPeriodSec = (int)endDate.atStartOfDay(Config.zoneId).toEpochSecond();
        this.startPeriodSec = (int)startDate.atStartOfDay(Config.zoneId).toEpochSecond();
        return this;
    }

}
