package com.stgsporting.piehmecup.entities;

import com.stgsporting.piehmecup.config.DatabaseEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.jetbrains.annotations.NotNull;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Entity(name = DatabaseEnum.iconsTable)
public class Icon extends BaseEntity {

    @Column(name = DatabaseEnum.name, nullable = false, unique = true)
    @NotNull
    private String name;

    @Column(name = DatabaseEnum.price, nullable = false)
    @NotNull
    private Integer price;

    @Column(name = DatabaseEnum.available, nullable = false)
    @NotNull
    private Boolean available;

    @Column(name = DatabaseEnum.iconImgLink, nullable = false)
    private String imgLink;

    @ManyToMany(mappedBy = "icons")
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<User> users;

    @ManyToOne
    @JoinColumn(name = DatabaseEnum.levelId, nullable = false)
    private Level level;

    public Boolean isDefault() {
        return switch (name) {
            case "DefaultIcon", "Default1", "Default" -> true;
            default -> false;
        };
    }

    public static String defaultIcon(SchoolYear schoolYear) {
        return switch (schoolYear.getName()) {
            case "e3dady" -> "DefaultIcon";
            case "App Store" -> "Default1";
            default -> "Default";
        };
    }
}
