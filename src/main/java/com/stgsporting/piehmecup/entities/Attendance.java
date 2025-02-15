package com.stgsporting.piehmecup.entities;

import com.stgsporting.piehmecup.config.DatabaseEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = DatabaseEnum.attendanceTable)
public class Attendance extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = DatabaseEnum.userId, nullable = false)
    private User user;

    @Column(name = DatabaseEnum.approved, nullable = false)
    @ColumnDefault("false")
    private Boolean approved;

    @JoinColumn(name = DatabaseEnum.priceId, nullable = false)
    @ManyToOne
    private Price price;

    @Column(name = DatabaseEnum.createdAt, nullable = false)
    private Timestamp createdAt;
}
