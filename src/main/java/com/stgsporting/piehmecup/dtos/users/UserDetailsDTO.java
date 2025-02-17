package com.stgsporting.piehmecup.dtos.users;

import com.stgsporting.piehmecup.entities.Icon;
import com.stgsporting.piehmecup.entities.User;
import com.stgsporting.piehmecup.services.FileService;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDetailsDTO {

    private Long id;
    private String username;
    private Integer coins;
    private Integer cardRating;
    private Double lineupRating;
    private String imageKey;
    private String imageUrl;
    private String selectedIcon;
    private List<AttendanceUserDTO> attendances;


    public UserDetailsDTO(User user, FileService fileService) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.coins = user.getCoins();
        this.cardRating = user.getCardRating();
        this.lineupRating = user.getLineupRating();
        Icon selectedIcon = user.getSelectedIcon();

        this.imageKey = user.getImgLink();
        this.imageUrl = fileService.generateSignedUrl(user.getImgLink());

        this.attendances = user.getAttendances().stream().map(AttendanceUserDTO::new).toList();

        this.selectedIcon = selectedIcon != null ? selectedIcon.getImgLink() : null;
    }
}
