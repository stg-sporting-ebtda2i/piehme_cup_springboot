package com.stgsporting.piehmecup.dtos.users;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
public class UserChangeImageDTO {
    private MultipartFile image;
}
