package com.sonia.java.bankcheckapplication.model.user.req;

public class MonoBankMerchantRequest {


    private String xToken;

    public String getXToken() {
        return xToken;
    }

    public void setXToken(String xToken) {
        this.xToken = xToken;
    }
    public MonoBankMerchantRequest() {
    }


    public MonoBankMerchantRequest(String xToken) {
        this.xToken = xToken;
    }


    @Override
    public String toString() {
        return "MonoBankMerchantRequest{" +
                "xToken='" + xToken + '\'' +
                '}';
    }
}
