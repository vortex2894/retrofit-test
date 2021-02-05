package com.retrofit.test.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenResponse {

    private String expiresIn;
    private String refreshToken;
    private String scope;
    private String tokenType;
    private String state;

    public TokenResponse addState(String state) {
        this.setState(state);
        return this;
    }
}
