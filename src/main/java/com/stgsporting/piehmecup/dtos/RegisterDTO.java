package com.stgsporting.piehmecup.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class RegisterDTO {
    private String username;
    private String password;

    @Override
    public String toString() {
        return "RegisterDTO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
