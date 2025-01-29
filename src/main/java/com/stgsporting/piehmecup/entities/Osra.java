package com.stgsporting.piehmecup.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.stgsporting.piehmecup.config.DatabaseEnum;
import jakarta.persistence.CascadeType;
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
@Entity(name = DatabaseEnum.osraTable)
public class Osra extends BaseEntity {

    @Column(name = DatabaseEnum.osraName, unique = true, nullable = false)
    private String name;

    @OneToMany(mappedBy = "osra", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<User> users;
}
