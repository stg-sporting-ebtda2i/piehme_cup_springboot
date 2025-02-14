package com.stgsporting.piehmecup.entities;

import com.stgsporting.piehmecup.config.DatabaseEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    @Column(name = DatabaseEnum.liturgyName, nullable = false)
    private String liturgyName;

    @Column(name = DatabaseEnum.createdAt, nullable = false)
    private Timestamp createdAt;
}
