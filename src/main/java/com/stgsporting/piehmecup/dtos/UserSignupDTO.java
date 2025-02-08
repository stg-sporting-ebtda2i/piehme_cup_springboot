package com.stgsporting.piehmecup.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignupDTO {
    private String username;
    private String password;
    private String imgLink;
    private String osra;

    @Override
    public String toString() {
        return "UserSignupDTO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", imgLink='" + imgLink + '\'' +
                ", osra='" + osra + '\'' +
                '}';
    }
}
