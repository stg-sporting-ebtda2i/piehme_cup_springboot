package com.stgsporting.piehmecup.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserCardDTO {
    private String name;
    private Integer cardRating;
    private String imageUrl;
    private String imageKey;
    private String iconUrl;
    private String iconKey;
    private String position;
}
