package com.spreadsearch.parserrs;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.spreadsearch.payload.request.BinanceRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

@Component
@Slf4j
@RequiredArgsConstructor
public class BinanceParser {
    private final RestTemplate restTemplate;
    private final String GET_COURSE_URL = "https://p2p.binance.com/bapi/c2c/v2/friendly/c2c/adv/search/";

    public double getCurrentCourse(String tradeType, int amount, String[] payTypes, String coin) throws IOException {
        BinanceRequest request = BinanceRequest.builder()
                .page(1)
                .rows(1)
                .payTypes(payTypes)
                .countries(new String[] {})
                .transAmount(amount)
                .fiat("RUB")
                .tradeType(tradeType)
                .asset(coin)
                .merchantCheck(false)
                .build();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<BinanceRequest> entity = new HttpEntity(request, headers);
        String jsonResponse = restTemplate.exchange(GET_COURSE_URL, HttpMethod.POST, entity, String.class).getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonResponse);
        JsonNode dataNode = rootNode.path("data");
        JsonNode advNode = dataNode.get(0).path("adv");
        JsonNode priceNode = advNode.path("price");
        return Double.parseDouble(priceNode.asText());
    }
}

