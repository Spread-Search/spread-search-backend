package com.spreadsearch.services;

import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

class QiwiServiceTest {

    @Test
    void qiwiOplat() throws URISyntaxException {
        QiwiService qiwiService = new QiwiService();
        qiwiService.qiwiOplat();
    }
}