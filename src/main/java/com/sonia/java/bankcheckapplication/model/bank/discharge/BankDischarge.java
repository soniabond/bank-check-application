package com.sonia.java.bankcheckapplication.model.bank.discharge;

import java.io.Serializable;
import java.util.Date;

public abstract class BankDischarge implements Serializable {
    protected Date trandate;
    protected Float cardamount;
    protected String terminal;
    protected String description;
    protected String bankName;


    public BankDischarge() {
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public Date getTrandate() {
        return trandate;
    }

    public void setTrandate(Date trandate) {
        this.trandate = trandate;
    }

    public Float getCardamount() {
        return cardamount;
    }

    public void setCardamount(Float cardamount) {
        this.cardamount = cardamount;
    }


    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
