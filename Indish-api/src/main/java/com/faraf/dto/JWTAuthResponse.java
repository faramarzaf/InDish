package com.faraf.dto;

public class JWTAuthResponse {

    private String accessToken;
    private String tokenType = "Bearer";
    private String expiresInMillis;


    public JWTAuthResponse(String accessToken, String expiresInMillis) {
        this.accessToken = accessToken;
        this.expiresInMillis = expiresInMillis;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public String getExpiresInMillis() {
        return expiresInMillis;
    }

    public void setExpiresInMillis(String expiresInMillis) {
        this.expiresInMillis = expiresInMillis;
    }
}
