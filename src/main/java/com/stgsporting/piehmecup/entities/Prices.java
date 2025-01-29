package com.stgsporting.piehmecup.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.stgsporting.piehmecup.config.DatabaseEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = DatabaseEnum.pricesTable)
public class Prices extends BaseEntity {

    @Column(name = DatabaseEnum.name, nullable = false, unique = true)
    private String name;

    @Column(name = DatabaseEnum.coins, nullable = false)
    private Integer coins;

}
