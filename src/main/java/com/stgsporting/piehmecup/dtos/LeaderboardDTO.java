package com.stgsporting.piehmecup.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LeaderboardDTO {
    private String name;
    private Integer cardRating;
    private String userImgLink;
    private String Position;
    private Double lineupRating;
    private String iconImgLink;
}
