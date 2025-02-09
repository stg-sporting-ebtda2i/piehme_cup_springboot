package com.stgsporting.piehmecup.dtos.admins;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdminFormDTO {
    private String username;
    private String password;
    private String role;
    private Long schoolYear;
}
