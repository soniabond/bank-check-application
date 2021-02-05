package com.sonia.java.bankcheckapplication.model.user.req;

public class PrivatBankMerchantRequest {

    private String merchantId;

    private String merchantSignature;

    private String cardNumber;

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantSignature() {
        return merchantSignature;
    }

    public void setMerchantSignature(String merchantSignature) {
        this.merchantSignature = merchantSignature;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public String toString() {
        return "PrivatBankMerchantRequest{" +
                "merchantId='" + merchantId + '\'' +
                ", merchantSignature='" + merchantSignature + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                '}';
    }
}
