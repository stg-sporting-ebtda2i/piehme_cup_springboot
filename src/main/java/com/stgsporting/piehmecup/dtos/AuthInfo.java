package com.stgsporting.piehmecup.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthInfo {
    private String JWTToken;
    private Long userId;
    private String username;

    @Override
    public String toString() {
        return "AuthInfo{" +
                "JWTToken='" + JWTToken + '\'' +
                ", userId=" + userId +
                ", username='" + username + '\'' +
                '}';
    }
}
