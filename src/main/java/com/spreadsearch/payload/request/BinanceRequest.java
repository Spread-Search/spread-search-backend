package com.spreadsearch.payload.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BinanceRequest {
    private int page;
    private int rows;
    private String[] payTypes;
    private String[] countries;
    private int transAmount;
    private String fiat;
    private String tradeType;
    private String asset;
    private boolean merchantCheck;

}
