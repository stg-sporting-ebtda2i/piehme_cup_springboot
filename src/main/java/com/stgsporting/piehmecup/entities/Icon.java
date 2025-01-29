package com.stgsporting.piehmecup.entities;

import com.stgsporting.piehmecup.config.DatabaseEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = DatabaseEnum.iconsTable)
public class Icon extends BaseEntity {

    @Column(name = DatabaseEnum.name, nullable = false, unique = true)
    private String name;

    @Column(name = DatabaseEnum.price, nullable = false)
    private Integer price;

    @Column(name = DatabaseEnum.available, nullable = false)
    private Boolean available;

    @Column(name = DatabaseEnum.iconImgLink, nullable = false, unique = true)
    private String imgLink;

    @ManyToMany(mappedBy = "icons")
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Walad> welad;
}
