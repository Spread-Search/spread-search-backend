package com.spreadsearch.services;

import com.spreadsearch.enums.Coin;
import com.spreadsearch.enums.OrderAction;
import com.spreadsearch.enums.PayMethod;
import com.spreadsearch.parserrs.BinanceParser;
import com.spreadsearch.payload.response.MainSpreadResponse;
import com.spreadsearch.payload.response.SpreadResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BinanceService {
    private final BinanceParser binanceParser;

    public MainSpreadResponse getSpreadResponse(int amount, List<PayMethod> payMethods) throws IOException {
        MainSpreadResponse mainSpreadResponse = new MainSpreadResponse();

        List<SpreadResponse> makeMakeSpreadResponse = getSpreadResponses(amount, payMethods, OrderAction.MAKE_MAKE);
        List<SpreadResponse> takeMakeSpreadResponse = getSpreadResponses(amount, payMethods, OrderAction.TAKE_MAKE);
        List<SpreadResponse> makeTakeSpreadResponse = getSpreadResponses(amount, payMethods, OrderAction.MAKE_TAKE);
        List<SpreadResponse> takeTakeSpreadResponse = getSpreadResponses(amount, payMethods, OrderAction.TAKE_TAKE);

        mainSpreadResponse.setMakeMakeSpread(makeMakeSpreadResponse);
        mainSpreadResponse.setTakeMakeSpread(takeMakeSpreadResponse);
        mainSpreadResponse.setMakeTakeResponse(makeTakeSpreadResponse);
        mainSpreadResponse.setTakeTakeResponse(takeTakeSpreadResponse);

        return mainSpreadResponse;
    }

    private List<SpreadResponse> getSpreadResponses(int amount, List<PayMethod> payMethods, OrderAction orderAction) throws IOException {
        List<SpreadResponse> spreadResponses = new ArrayList<>();
        Map<PayMethod, Double> buy = null;
        Map<PayMethod, Double> sell = null;
        switch (orderAction) {
            case MAKE_MAKE:
                buy = getCoinCourse("SELL", amount, payMethods, Coin.USDT);
                sell = getCoinCourse("BUY", amount, payMethods, Coin.USDT);
            case TAKE_MAKE:
                buy = getCoinCourse("BUY", amount, payMethods, Coin.USDT);
                sell = getCoinCourse("BUY", amount, payMethods, Coin.USDT);
            case MAKE_TAKE:
                buy = getCoinCourse("SELL", amount, payMethods, Coin.USDT);
                sell = getCoinCourse("SELL", amount, payMethods, Coin.USDT);
            case TAKE_TAKE:
                buy = getCoinCourse("BUY", amount, payMethods, Coin.USDT);
                sell = getCoinCourse("SELL", amount, payMethods, Coin.USDT);
        }
        for (Map.Entry<PayMethod, Double> buys : buy.entrySet()) {
            for (Map.Entry<PayMethod, Double> sells : sell.entrySet()) {
                SpreadResponse spreadResponse = new SpreadResponse();
                spreadResponse.setFirstPayMethod(buys.getKey().getLabelName());
                spreadResponse.setSecondPayMethod(sells.getKey().getLabelName());
                spreadResponse.setUsdtSpread(getSpread(buys.getValue(), sells.getValue()));
                spreadResponses.add(spreadResponse);
            }
        }
        return spreadResponses;
    }

    private Map<PayMethod, Double> getCoinCourse(String action, int amount, List<PayMethod> payMethods, Coin coin) throws IOException {
        Map<PayMethod, Double> courseCrypto = new HashMap<>();
        for (PayMethod payMethod : payMethods) {
            double coinCourse = binanceParser.getCurrentCourse(action, amount, new String[]{payMethod.getApiName()}, coin.getApiName());
            courseCrypto.put(payMethod, coinCourse);
        }
        return courseCrypto;
    }

    private String getSpread(double firstCourse, double secondCourse) {
        double after = 10000 / firstCourse;
        double before = after * secondCourse;
        double result = before - 10000;
        double percent = (result * 100) / 10000;
        String resultInString = String.format("%.2f", percent);
        if (resultInString.endsWith("0")) {
            return resultInString.substring(0, 2);
        }
        return resultInString;
    }
}
