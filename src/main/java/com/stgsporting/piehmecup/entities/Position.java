package com.stgsporting.piehmecup.entities;

import com.stgsporting.piehmecup.config.DatabaseEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
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
@Entity(name = DatabaseEnum.positionsTable)
public class Position extends BaseEntity {

    @Column(name = DatabaseEnum.name, nullable = false, unique = true)
    private String name;

    @Column(name = DatabaseEnum.price, nullable = false)
    private Integer price;

    @ManyToMany(mappedBy = "positions")
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Walad> welad;
}
