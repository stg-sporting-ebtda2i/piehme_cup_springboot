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

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = DatabaseEnum.attendanceTable)
public class Attendance extends BaseEntity{

    @Column(name = DatabaseEnum.timestamp, nullable = false)
    private Timestamp timestamp;

    @Column(name = DatabaseEnum.approved, nullable = false)
    private Boolean approved;

    @ManyToOne
    @JoinColumn(name = DatabaseEnum.attendedLiturgy, nullable = false)
    private Walad walad;

}
