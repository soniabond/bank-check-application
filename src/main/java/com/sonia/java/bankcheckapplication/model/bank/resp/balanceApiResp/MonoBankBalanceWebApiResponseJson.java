package com.sonia.java.bankcheckapplication.model.bank.resp.balanceApiResp;

import java.util.List;

public class MonoBankBalanceWebApiResponseJson {
    private  String clientId;
    private String name;
    private String webHookUrl;

    @Override
    public String toString() {
        return "MonoBankBalanceResponse{" +
                "clientId='" + clientId + '\'' +
                ", name='" + name + '\'' +
                ", webHookUrl='" + webHookUrl + '\'' +
                ", permissions='" + permissions + '\'' +
                ", accounts=" + accounts +
                '}';
    }

    public MonoBankBalanceWebApiResponseJson() {
    }

    public MonoBankBalanceWebApiResponseJson(MonoBankBalanceWebApiResponseJson monoBankBalanceWebApiResponseJson) {
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebHookUrl() {
        return webHookUrl;
    }

    public void setWebHookUrl(String webHookUrl) {
        this.webHookUrl = webHookUrl;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    private String permissions;
    private List<Account> accounts;
}
