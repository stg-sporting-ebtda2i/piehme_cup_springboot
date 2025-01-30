package com.stgsporting.piehmecup.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.stgsporting.piehmecup.config.DatabaseEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = DatabaseEnum.weladTable)
public class Walad extends BaseEntity {

    @Column(name = DatabaseEnum.currentCoins, nullable = false)
    private Integer currentCoins;

    @Column(name = DatabaseEnum.cardRating, nullable = false)
    private Integer cardRating;

    @Column(name = DatabaseEnum.lineupRating, nullable = false)
    private Double lineupRating;

    @Column(name = DatabaseEnum.waladImgLink, unique = true)
    private String imgLink;

    @ManyToMany
    @JoinTable(name = DatabaseEnum.ownedPlayersTable, joinColumns = @JoinColumn(name = DatabaseEnum.waladId),
            inverseJoinColumns = @JoinColumn(name = DatabaseEnum.playerId))
    private List<Player> players;

    @ManyToMany
    @JoinTable(name = DatabaseEnum.ownedIconsTable, joinColumns =  @JoinColumn(name = DatabaseEnum.waladId),
            inverseJoinColumns = @JoinColumn(name = DatabaseEnum.iconId))
    private List<Icon> icons;

    @ManyToMany
    @JoinTable(name = DatabaseEnum.ownedPositionsTable, joinColumns = @JoinColumn(name = DatabaseEnum.waladId),
            inverseJoinColumns = @JoinColumn(name = DatabaseEnum.positionId))
    private List<Position> positions;

    @OneToMany(mappedBy = "walad", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Attendance> attendances;
}
