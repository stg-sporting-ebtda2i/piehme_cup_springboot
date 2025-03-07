package com.stgsporting.piehmecup.dtos.players;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerDTO {
    private Long id;
    private String name;
    private String position;
    private Integer rating;
    private Boolean available;
    private String imageUrl;
    private String imageKey;
    private Integer price;

    @Override
    public String toString() {
        return "PlayerDTO{" +
                "name='" + name + '\'' +
                ", position=" + position +
                ", rating=" + rating +
                ", available=" + available +
                ", imgLink='" + imageUrl + '\'' +
                ", price=" + price +
                '}';
    }
}
