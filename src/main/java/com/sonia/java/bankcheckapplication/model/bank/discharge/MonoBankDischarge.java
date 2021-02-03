package com.sonia.java.bankcheckapplication.model.bank.discharge;

import com.sonia.java.bankcheckapplication.model.bank.resp.MonoBankDischargeResponse;

import java.util.Date;
import java.util.Objects;

public class MonoBankDischarge extends BankDischarge {

    public MonoBankDischarge() {

    }

    public MonoBankDischarge(MonoBankDischargeResponse dischargeResponse){
        this.cardamount = (float)dischargeResponse.getOperationAmount()/100;
        this.terminal = dischargeResponse.getDescription();
        this.description = dischargeResponse.getComment();
        this.trandate = new Date(Long.parseLong(dischargeResponse.getTime())*1000);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MonoBankDischarge that = (MonoBankDischarge) o;
        return  Objects.equals(trandate, that.trandate) &&  Objects.equals(cardamount, that.cardamount) && Objects.equals(terminal, that.terminal) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trandate, cardamount, terminal, description);
    }

    @Override
    public String toString() {
        return "trandate='" + trandate + '\'' +
                ", cardamount='" + cardamount + '\'' +
                ", terminal='" + terminal + '\'' +
                ", description='" + description + '\'';
    }


}
