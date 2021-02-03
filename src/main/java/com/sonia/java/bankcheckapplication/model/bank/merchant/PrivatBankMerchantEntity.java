package com.sonia.java.bankcheckapplication.model.bank.merchant;

import com.sonia.java.bankcheckapplication.ApplicationContextProvider;
import com.sonia.java.bankcheckapplication.model.bank.Bank;
import com.sonia.java.bankcheckapplication.model.bank.PrivatBank;
import org.hibernate.validator.constraints.CodePointLength;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;


@Entity
@Table(name = "privat_bank_merchants")
@DiscriminatorValue("PRIVAT")
public class PrivatBankMerchantEntity extends BankMerchantEntity {

    @Column(name = "merchant_id")
    private String merchantId;

    @Column(name = "merchant_signature")
    private String merchantSignature;

    @Column(name = "card_number")
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

    public PrivatBankMerchantEntity() {
      }

    @Override
    public String toString() {
        return "PrivatBankMerchant{" +
                "merchantId='" + merchantId + '\'' +
                ", merchantSignature='" + merchantSignature + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrivatBankMerchantEntity that = (PrivatBankMerchantEntity) o;
        return merchantId.equals(that.merchantId) && merchantSignature.equals(that.merchantSignature) && cardNumber.equals(that.cardNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(merchantId, merchantSignature, cardNumber);
    }

    @Override
    public Bank getBank() {
        return ApplicationContextProvider.AppContext.getApplicationContext().getBean("privatBank", PrivatBank.class);
    }
}