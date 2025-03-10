package com.stgsporting.piehmecup.dtos;

import com.stgsporting.piehmecup.entities.Price;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PriceDTO {
    private Long id;
    private String name;
    private Integer coins;

    public PriceDTO(Price price) {
        this.id = price.getId();
        this.name = price.getName();
        this.coins = price.getCoins();
    }
}
