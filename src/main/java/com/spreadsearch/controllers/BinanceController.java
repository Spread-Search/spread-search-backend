package com.spreadsearch.controllers;

import com.spreadsearch.enums.PayMethod;
import com.spreadsearch.payload.response.MainSpreadResponse;
import com.spreadsearch.services.BinanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/binance/")
@RequiredArgsConstructor
public class BinanceController {
    private final BinanceService binanceService;

    @GetMapping
    public ResponseEntity<MainSpreadResponse> getCourse(@RequestParam(defaultValue = "10000", required = false) int amount,
                                                        @RequestParam(required = false) String[] payMethods) throws IOException {
        MainSpreadResponse spreadResponse =
                binanceService.getSpreadResponse(amount, getPayMethods(payMethods));
        return new ResponseEntity<>(spreadResponse, HttpStatus.OK);
    }

    private List<PayMethod> getPayMethods(String[] payMethods) {
        List<PayMethod> payMethodsInList = new ArrayList<>();
        if (payMethods == null) { payMethodsInList.addAll(Arrays.asList(PayMethod.values()));
        } else {
            for (String payMethod : payMethods) {
                for (PayMethod payMethodValue : PayMethod.values()) {
                    if (payMethod.equals(payMethodValue.getApiName())) {
                        payMethodsInList.add(payMethodValue);
                    }
                }
            }
        }
        return payMethodsInList;
    }
}
