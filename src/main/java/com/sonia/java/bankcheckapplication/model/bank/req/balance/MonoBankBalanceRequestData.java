package com.sonia.java.bankcheckapplication.model.bank.req.balance;

import com.sonia.java.bankcheckapplication.model.bank.merchant.BankMerchantEntity;
import com.sonia.java.bankcheckapplication.model.bank.merchant.MonoBankMerchantEntity;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Scope("prototype")
public class MonoBankBalanceRequestData extends BalanceRequestData{

    private Map<String, String> headers = new HashMap<>();

    private final HttpMethod httpMethod = HttpMethod.GET;

    private final String url = "https://api.monobank.ua/personal/client-info";

    private String xToken;

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public String getUrl() {
        return url;
    }

    public String getxToken() {
        return xToken;
    }

    public void setxToken(String xToken) {
        this.xToken = xToken;
    }

    public String getXToken() {
        return xToken;
    }

    public void setXToken(String xToken) {
        this.xToken = xToken;
    }

    @Override
    public MonoBankBalanceRequestData nestMerchant(BankMerchantEntity merchant) {
        MonoBankMerchantEntity monoBankMerchant = (MonoBankMerchantEntity) merchant;
        this.headers.put("X-Token", monoBankMerchant.getxToken());
        return this;

    }

    @Override
    public String toString() {
        return url;
    }
}
