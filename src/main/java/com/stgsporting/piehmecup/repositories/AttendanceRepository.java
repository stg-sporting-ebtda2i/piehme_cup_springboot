package com.stgsporting.piehmecup.repositories;

import com.stgsporting.piehmecup.entities.Attendance;
import com.stgsporting.piehmecup.entities.SchoolYear;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    @Query("SELECT a FROM ATTENDANCE a WHERE a.approved = :approved AND a.user.schoolYear = :schoolYear order by a.id desc")
    Page<Attendance> findByApprovedAndUserContainingSchoolYear(Pageable pageable, @Param("approved") Boolean approved, @Param("schoolYear") SchoolYear schoolYear);
}
