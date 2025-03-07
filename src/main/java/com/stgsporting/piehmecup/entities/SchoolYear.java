package com.stgsporting.piehmecup.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.stgsporting.piehmecup.config.DatabaseEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = DatabaseEnum.schoolYearTable)
public class SchoolYear extends BaseEntity {

    @Column(name = DatabaseEnum.schoolYearName, unique = true, nullable = false)
    private String name;

    @OneToMany(mappedBy = "schoolYear", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<User> users;

    @ManyToOne
    @JoinColumn(name = DatabaseEnum.levelId, nullable = false)
    private Level level;

    public String getSlug() {
        return name.toLowerCase().replace(" ", "-");
    }
}
