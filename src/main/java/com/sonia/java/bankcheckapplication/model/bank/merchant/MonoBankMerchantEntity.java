package com.sonia.java.bankcheckapplication.model.bank.merchant;

import com.sonia.java.bankcheckapplication.ApplicationContextProvider;
import com.sonia.java.bankcheckapplication.model.bank.Bank;
import com.sonia.java.bankcheckapplication.model.bank.MonoBank;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;


@Entity
@Table(name = "mono_bank_merchants")
@DiscriminatorValue("MONO")
public class MonoBankMerchantEntity extends BankMerchantEntity {

    @Column(name = "x_token")
    private String xToken;

    public String getxToken() {
        return xToken;
    }

    public void setxToken(String xToken) {
        this.xToken = xToken;
    }

    public MonoBankMerchantEntity() {
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MonoBankMerchantEntity that = (MonoBankMerchantEntity) o;
        return xToken.equals(that.xToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(xToken);
    }

    @Override
    public Bank getBank() {
        return ApplicationContextProvider.AppContext.getApplicationContext().getBean("monoBank", MonoBank.class);
    }
}
