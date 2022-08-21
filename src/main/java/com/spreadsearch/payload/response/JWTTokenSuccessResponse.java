package com.spreadsearch.payload.response;

import lombok.Data;

@Data
public class JWTTokenSuccessResponse {
    private String jwt;
    private Boolean isSuccess;

    public JWTTokenSuccessResponse(String jwt, Boolean isSuccess) {
        this.jwt = jwt;
        this.isSuccess = isSuccess;
    }
}
