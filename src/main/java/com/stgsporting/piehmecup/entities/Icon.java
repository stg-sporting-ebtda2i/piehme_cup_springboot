package com.stgsporting.piehmecup.entities;

import com.stgsporting.piehmecup.config.DatabaseEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.jetbrains.annotations.NotNull;

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

    @Column(name = DatabaseEnum.iconImgLink, nullable = false, unique = true)
    private String imgLink;

    @ManyToMany(mappedBy = "icons")
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<User> user;
}
