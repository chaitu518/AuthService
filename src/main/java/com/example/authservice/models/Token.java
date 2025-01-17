package com.example.authservice.models;

public class Token {
    private String token;
    private String expiryTime;

    public String getToken() {
        return this.token;
    }

    public String getExpiryTime() {
        return this.expiryTime;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setExpiryTime(String expiryTime) {
        this.expiryTime = expiryTime;
    }
}
