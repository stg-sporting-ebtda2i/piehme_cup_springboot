package com.stgsporting.piehmecup.dtos.icons;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IconDTO {
    private Long id;
    private String name;
    private String imgLink;
    private String imageUrl;
    private Integer price;
    private Boolean available;
}
