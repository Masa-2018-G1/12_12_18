package com.sheygam.masa_2018_g1_12_12_18;

public class AuthResponseDto {
    private String token;

    public AuthResponseDto() {
    }

    public AuthResponseDto(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "AuthResponseDto{" +
                "token='" + token + '\'' +
                '}';
    }
}
