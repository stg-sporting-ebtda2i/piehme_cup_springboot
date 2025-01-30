package com.stgsporting.piehmecup.entities;

import com.stgsporting.piehmecup.config.DatabaseEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = DatabaseEnum.ostazTable)
public class Ostaz extends BaseEntity {
    @OneToOne
    @JoinColumn(name = DatabaseEnum.ostazId)
    private User user;
}
