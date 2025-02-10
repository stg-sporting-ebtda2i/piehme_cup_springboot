package com.stgsporting.piehmecup.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterDTO extends RegisterDTO {
    private String imgLink;
    private String schoolYear;

    @Override
    public String toString() {
        return "UserRegisterDTO{" +
                "username='" + getUsername() + '\'' +
                ", password='" + getPassword()  + '\'' +
                ", imgLink='" + imgLink + '\'' +
                ", schoolYear='" + schoolYear + '\'' +
                '}';
    }
}
