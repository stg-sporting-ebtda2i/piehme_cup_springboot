package com.stgsporting.piehmecup.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthUserInfo {
    private String JWTToken;
    private Long userId;
    private String username;
}
