package com.stgsporting.piehmecup.entities;

import com.stgsporting.piehmecup.config.DatabaseEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity(name = DatabaseEnum.adminsTable)
public class Admin extends BaseEntity {
    @OneToOne
    @JoinColumn(name = DatabaseEnum.adminId)
    private User user;
}