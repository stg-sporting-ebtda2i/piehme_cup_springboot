package com.stgsporting.piehmecup.dtos.users;

import com.stgsporting.piehmecup.entities.Icon;
import com.stgsporting.piehmecup.entities.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInListDTO {

    private Long id;
    private String username;
    private Integer coins;
    private Integer cardRating;
    private Double lineupRating;
    private String imgLink;
    private String selectedIcon;


    public UserInListDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.coins = user.getCoins();
        this.cardRating = user.getCardRating();
        this.imgLink = user.getImgLink();
        this.lineupRating = user.getLineupRating();
        Icon selectedIcon = user.getSelectedIcon();

        this.selectedIcon = selectedIcon != null ? selectedIcon.getImgLink() : null;
    }
}
