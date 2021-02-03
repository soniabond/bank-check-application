package com.sonia.java.bankcheckapplication.model.bank.dataReciever;

import com.sonia.java.bankcheckapplication.model.bank.discharge.BankDischarge;
import com.sonia.java.bankcheckapplication.model.bank.req.balance.BalanceRequestData;
import com.sonia.java.bankcheckapplication.model.bank.req.discharge.DischargeRequestData;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class BankDataReceiver implements Subject{
    protected List<Observer> observers;
    protected Object discharge;

    public BankDataReceiver(){
        observers = new ArrayList<>();
    }

    public abstract String receiveDischarge(DischargeRequestData dischargeRequestData);

    public abstract String receiveBalance(BalanceRequestData balanceRequestData);

    public Object getDischarge() {
        return discharge;
    }

    public void setDischarge(BankDischarge discharge) {
        this.discharge = discharge;
    }

    @Override
    public void registerObserver(Observer observer) {
        if (!observers.contains(observer)){
            observers.add(observer);
        }
    }

    @Override
    public void removeObserver(Observer observer) {
        if (observers.contains(observer))
            observers.remove(observer);

    }

    @Override
    public void notifyObservers() {
        for (Observer observer: observers){
            observer.update();
        }

    }
}
