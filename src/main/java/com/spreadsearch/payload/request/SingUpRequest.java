package com.spreadsearch.payload.request;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SingUpRequest {
    private String email;
    private String username;
    private String password;
    private String confirmPassword;
}
