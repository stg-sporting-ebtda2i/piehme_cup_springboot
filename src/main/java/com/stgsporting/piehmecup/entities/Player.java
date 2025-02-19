package com.stgsporting.piehmecup.entities;

import com.stgsporting.piehmecup.config.DatabaseEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = DatabaseEnum.playersTable)
public class Player extends BaseEntity {

    @Column(name = DatabaseEnum.rating, nullable = false)
    private Integer rating;

    @Column(name = DatabaseEnum.available, nullable = false)
    private Boolean available;

    @Column(name = DatabaseEnum.playerImgLink, nullable = false)
    private String imgLink;

    @Column(name = DatabaseEnum.price, nullable = false)
    private Integer price;

    @Column(name = DatabaseEnum.name, nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = DatabaseEnum.positionId, nullable = false)
    private Position position;

    @ManyToMany(mappedBy = "players")
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<User> user;
}
