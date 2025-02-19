package com.stgsporting.piehmecup.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import net.jcip.annotations.Immutable;
import org.hibernate.annotations.Subselect;

@Setter
@Getter
@Entity
@Immutable
@Table(name = "`user_rating`")
public class UserRating {

    @Id
    private Long userId;

    @Column(name = "lineup_rating")
    private Double lineupRating;
}
