package com.sonia.java.bankcheckapplication.model.bank.resp;

import java.io.Serializable;

public class MonoBankDischargeResponse implements Serializable {

    private String id;

    private String time;

    private String description;

    private int mcc;

    private long amount;

    private long operationAmount;

    private int currencyCode;

    private float commissionRate;

    private long cashbackAmount;

    private long balance;

    private boolean hold;

    private String receiptId;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    private String comment;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMcc() {
        return mcc;
    }

    public void setMcc(int mcc) {
        this.mcc = mcc;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public long getOperationAmount() {
        return operationAmount;
    }

    public void setOperationAmount(long operationAmount) {
        this.operationAmount = operationAmount;
    }

    public int getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(int currencyCode) {
        this.currencyCode = currencyCode;
    }

    public float getCommissionRate() {
        return commissionRate;
    }

    public void setCommissionRate(float commissionRate) {
        this.commissionRate = commissionRate;
    }

    public long getCashbackAmount() {
        return cashbackAmount;
    }

    public void setCashbackAmount(long cashbackAmount) {
        this.cashbackAmount = cashbackAmount;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public boolean isHold() {
        return hold;
    }

    public void setHold(boolean hold) {
        this.hold = hold;
    }

    public String getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(String receiptId) {
        this.receiptId = receiptId;
    }

    @Override
    public String toString() {
        return "MonoBankDischargeResponse{" +
                "id='" + id + '\'' +
                ", time='" + time + '\'' +
                ", description='" + description + '\'' +
                ", amount='" + amount + '\'' +
                ", operationAmount='" + operationAmount + '\'' +
                '}';
    }


}
