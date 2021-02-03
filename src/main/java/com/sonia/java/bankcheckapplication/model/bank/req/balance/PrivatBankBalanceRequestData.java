package com.sonia.java.bankcheckapplication.model.bank.req.balance;

import com.sonia.java.bankcheckapplication.model.bank.merchant.BankMerchantEntity;
import com.sonia.java.bankcheckapplication.model.bank.merchant.PrivatBankMerchantEntity;
import com.sonia.java.bankcheckapplication.util.HashCoding;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class PrivatBankBalanceRequestData extends BalanceRequestData{

    private String cardNumber;

    private String merchantId;

    private String merchantSignature;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private String url = "https://api.privatbank.ua/p24api/balance";

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }


    @Override
    public PrivatBankBalanceRequestData nestMerchant (BankMerchantEntity bankMerchantEntity){
        PrivatBankMerchantEntity privatBankMerchant = (PrivatBankMerchantEntity) bankMerchantEntity;
        this.merchantId = privatBankMerchant.getMerchantId();
        this.merchantSignature = privatBankMerchant.getMerchantSignature();
        this.cardNumber = privatBankMerchant.getCardNumber();
        return this;
    }

    private String getSignature(){
        String signature =  "<oper>cmt</oper>" +
                "<wait>0</wait>" +
                "<test>0</test>" +
                "<payment id=\"\">" +
                "                    <prop name=\"cardnum\" value=\""+this.cardNumber+"\" />" +
                "                    <prop name=\"country\" value=\"UA\" />" +
                "                    </payment>" +this.merchantSignature;
        signature = HashCoding.hashSignatureForPrivatBank(signature);
        return signature;
    }

    public String formRequestBody(){
        return  "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<request version=\"1.0\">" +
                "<merchant>" +
                "<id>"+this.merchantId+"</id>" +
                "<signature>"+this.getSignature()+"</signature>" +
                "</merchant>" +
                "<data>" +
                "<oper>cmt</oper>" +
                "<wait>0</wait>" +
                "<test>0</test>" +
                "<payment id=\"\">" +
                "                    <prop name=\"cardnum\" value=\""+this.cardNumber+"\" />" +
                "                    <prop name=\"country\" value=\"UA\" />" +
                "                    </payment>" +
                "</data>"+
                "</request>";
    }

}
