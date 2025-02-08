package com.stgsporting.piehmecup.entities;

import com.stgsporting.piehmecup.config.DatabaseEnum;
import com.stgsporting.piehmecup.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnTransformer;

@Setter
@Getter
@Entity(name = DatabaseEnum.adminsTable)
public class Admin extends BaseEntity {

    @Column(name = DatabaseEnum.username, nullable = false, unique = true)
    private String username;

    @Column(name = DatabaseEnum.password, nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = DatabaseEnum.role, nullable = false)
    @ColumnTransformer(write = "?::ROLE")
    private Role role;
}