package com.stgsporting.piehmecup.dtos.admins;

import com.stgsporting.piehmecup.dtos.schoolYears.SchoolYearInListDTO;
import com.stgsporting.piehmecup.entities.Admin;
import com.stgsporting.piehmecup.entities.SchoolYear;
import com.stgsporting.piehmecup.enums.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdminInListDTO {
    private Long id;
    private String username;
    private Role role;
    private SchoolYearInListDTO schoolYear;

    public AdminInListDTO(Admin admin) {
        this.id = admin.getId();
        this.username = admin.getUsername();
        this.role = admin.getRole();
        this.schoolYear = new SchoolYearInListDTO(admin.getSchoolYear());
    }
}
