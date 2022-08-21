package com.spreadsearch.enums;

public enum PayMethod {
    TINKOFF("Tinkoff", "Тинькофф"),
    ROSBANK("RosBank", "Росбанк"),
    RAIFFEISEN_BANK("RaiffeisenBankRussia", "Райффайзен банк"),
    QIWI("QIWI", "QIWI"),
    YANDEX_MONEY("YandexMoneyNew", "ЮMoney"),
    MOBILE("Mobiletopup", "Моб. баланс");

    private String apiName;
    private String labelName;


    PayMethod(String apiName, String labelName) {
        this.apiName = apiName;
        this.labelName = labelName;
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }
}
