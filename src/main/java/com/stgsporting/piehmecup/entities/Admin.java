package com.stgsporting.piehmecup.entities;

import com.stgsporting.piehmecup.authentication.Authenticatable;
import com.stgsporting.piehmecup.config.DatabaseEnum;
import com.stgsporting.piehmecup.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Setter
@Getter
@Entity(name = DatabaseEnum.adminsTable)
public class Admin extends BaseEntity implements Authenticatable {

    @Column(name = DatabaseEnum.username, nullable = false, unique = true)
    private String username;

    @Column(name = DatabaseEnum.password, nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name = DatabaseEnum.schoolYearId, nullable = false)
    private SchoolYear schoolYear;

    @Enumerated(EnumType.STRING)
    @Column(name = DatabaseEnum.role, nullable = false)
    private Role role;

    public void setPassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

        this.password = encoder.encode(password);
    }

    public String getRoleString() {
        return getRole().name();
    }

    @Override
    public Boolean getConfirmed() {
        return false;
    }

    public boolean hasAccessTo(User user) {
        return user.getSchoolYear().getId().equals(getSchoolYear().getId());
    }

    public boolean equals(Admin admin) {
        return admin.getId().equals(this.getId());
    }
}