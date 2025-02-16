package com.stgsporting.piehmecup.entities;

import com.stgsporting.piehmecup.config.DatabaseEnum;
import com.stgsporting.piehmecup.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = DatabaseEnum.buttonsTable)
public class ButtonsVisibility extends BaseEntity {

    @Column(name = DatabaseEnum.buttonName, nullable = false, unique = true)
    private String name;

    @Column(name = DatabaseEnum.visible, nullable = false)
    private Boolean visible;

    @Column(name = DatabaseEnum.role, nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;
}
