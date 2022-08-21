package com.spreadsearch.enums;

public enum Coin {
    USDT("USDT"),
    BTC("BTC"),
    BUSD("BUSD"),
    BNB("BNB"),
    ETH("ETH"),
    RUB("RUB"),
    SHIB("SHIB");

    private String apiName;

    Coin(String apiName) {
        this.apiName = apiName;
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }
}
