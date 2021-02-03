package com.sonia.java.bankcheckapplication.model.bank.balance;

import com.sonia.java.bankcheckapplication.model.bank.resp.balanceApiResp.Account;
import com.sonia.java.bankcheckapplication.model.bank.resp.balanceApiResp.MonoBankBalanceWebApiResponseJson;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MonoBankBalance {

    private List<String> accounts = new ArrayList<>();
    private float balance = 0;

    public static MonoBankBalance fromMonoBankBalanceWebApiResponseJson(
            MonoBankBalanceWebApiResponseJson webApiResponseJson
    ){
        MonoBankBalance monoBankBalance = new MonoBankBalance();
        List<String> accs = webApiResponseJson.getAccounts()
                .stream()
                .map(Account::getId)
                .collect(Collectors.toList());
        monoBankBalance.setAccounts(accs);
        float bal =0;
        for(Account account: webApiResponseJson.getAccounts()){
            bal += account.getBalance();
        }
        monoBankBalance.setBalance(bal/100);
        return monoBankBalance;

    }
    public List<String> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<String> accounts) {
        this.accounts = accounts;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "MonoBankBalance{" +
                "accounts=" + accounts +
                ", balance=" + balance +
                '}';
    }
}
