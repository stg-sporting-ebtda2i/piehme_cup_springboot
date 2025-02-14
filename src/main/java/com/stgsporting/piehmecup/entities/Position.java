package com.stgsporting.piehmecup.entities;

import com.stgsporting.piehmecup.config.DatabaseEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Entity(name = DatabaseEnum.positionsTable)
public class Position extends BaseEntity {

    @Column(name = DatabaseEnum.name, nullable = false, unique = true)
    @NotNull
    private String name;

    @Column(name = DatabaseEnum.price, nullable = false)
    @NotNull
    private Integer price;

    @ManyToMany(mappedBy = "positions")
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<User> user;
}
