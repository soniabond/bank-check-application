package com.sonia.java.bankcheckapplication.model.bank.dataReciever;

public interface Subject {
    public void registerObserver(Observer observer);
    public void removeObserver(Observer observer);
    public void notifyObservers();
}