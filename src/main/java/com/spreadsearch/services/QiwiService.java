package com.spreadsearch.services;

import com.qiwi.billpayments.sdk.client.BillPaymentClient;
import com.qiwi.billpayments.sdk.client.BillPaymentClientFactory;
import com.qiwi.billpayments.sdk.model.MoneyAmount;
import com.qiwi.billpayments.sdk.model.in.CreateBillInfo;
import com.qiwi.billpayments.sdk.model.in.Customer;
import com.qiwi.billpayments.sdk.model.in.PaymentInfo;
import com.qiwi.billpayments.sdk.model.out.BillResponse;

import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.Currency;
import java.util.UUID;

public class QiwiService {
    public void qiwiOplat() throws URISyntaxException {
        String secretKey = "48e7qUxn9T7RyYE1MVZswX1FRSbE6iyCj2gCRwwF3Dnh5XrasNTx3BGPiMsyXQFNKQhvukniQG8RTVhYm3iPvYoGUhFhm63QZ8WamZc9ufZE1dgVjiYU4bVaunmk5HGVaR5dAyogjD29xPCTWkanMZ2iWaeLuuj2QrPLvXhjsw8xPnwYD3QGyrHLwgETx";

        BillPaymentClient client = BillPaymentClientFactory.createDefault(secretKey);
        CreateBillInfo billInfo = new CreateBillInfo(
                UUID.randomUUID().toString(),
                new MoneyAmount(
                        BigDecimal.valueOf(1),
                        Currency.getInstance("RUB")
                ),
                "comment",
                ZonedDateTime.now().plusDays(45),
                new Customer(
                        "example@mail.org",
                        UUID.randomUUID().toString(),
                        "79123456789"
                ),
                "http://example.com/success"
        );

        String paymentUrl = client.createPaymentForm(new PaymentInfo(secretKey, new MoneyAmount(
                BigDecimal.valueOf(1),
                Currency.getInstance("RUB")), UUID.randomUUID().toString(),                 "http://example.com/success"));
        System.out.println(paymentUrl);
    }
}
