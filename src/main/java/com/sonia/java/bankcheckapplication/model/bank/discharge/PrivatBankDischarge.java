package com.sonia.java.bankcheckapplication.model.bank.discharge;

import java.util.Objects;

public class PrivatBankDischarge extends BankDischarge {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrivatBankDischarge that = (PrivatBankDischarge) o;
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
