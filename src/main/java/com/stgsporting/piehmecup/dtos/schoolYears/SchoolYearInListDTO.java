package com.stgsporting.piehmecup.dtos.schoolYears;

import com.stgsporting.piehmecup.entities.SchoolYear;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SchoolYearInListDTO {
    private Long id;
    private String name;

    public SchoolYearInListDTO(SchoolYear schoolYear) {
        this.id = schoolYear.getId();
        this.name = schoolYear.getName();
    }
}
