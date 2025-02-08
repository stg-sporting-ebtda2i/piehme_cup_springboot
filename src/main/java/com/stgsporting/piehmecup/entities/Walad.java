package com.stgsporting.piehmecup.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.stgsporting.piehmecup.config.DatabaseEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = DatabaseEnum.weladTable)
public class Walad extends BaseEntity {

    @Column(name = DatabaseEnum.coins, nullable = false)
    @ColumnDefault("0")
    private Integer coins;

    @Column(name = DatabaseEnum.cardRating, nullable = false)
    @ColumnDefault("50")
    private Integer cardRating;

    @Column(name = DatabaseEnum.lineupRating, nullable = false)
    @ColumnDefault("0")
    private Double lineupRating;

    @Column(name = DatabaseEnum.waladImgLink, unique = true)
    private String imgLink;

    @OneToOne
    @JoinColumn(name = DatabaseEnum.waladId)
    private User user;

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
