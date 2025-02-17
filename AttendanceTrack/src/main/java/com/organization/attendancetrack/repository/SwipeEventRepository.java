package com.organization.attendancetrack.repository;

import com.organization.attendancetrack.entity.SwipeEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface SwipeEventRepository extends JpaRepository<SwipeEventEntity, Long> {

    @Query("SELECT se FROM SwipeEventEntity se WHERE se.employeeId = :employeeId AND CAST(se.officeIn AS date) = :officeIn")
    Optional<SwipeEventEntity> findByEmployeeIdAndOfficeIn(Long employeeId, LocalDate officeIn);
}
