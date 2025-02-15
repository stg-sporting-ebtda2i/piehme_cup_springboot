package com.stgsporting.piehmecup.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CoinsDTO {
    private Long userId;
    private Integer coins;
    private String description;
}
