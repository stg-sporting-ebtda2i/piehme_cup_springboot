package com.stgsporting.piehmecup.entities;

import com.stgsporting.piehmecup.config.DatabaseEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = DatabaseEnum.pricesTable)
public class Price extends BaseEntity {

    @Column(name = DatabaseEnum.liturgyName, nullable = false, unique = true)
    private String name;

    @Column(name = DatabaseEnum.coins, nullable = false)
    private Integer coins;

    @ManyToOne
    @JoinColumn(name = DatabaseEnum.schoolYearId, nullable = false)
    private SchoolYear schoolYear;

}
