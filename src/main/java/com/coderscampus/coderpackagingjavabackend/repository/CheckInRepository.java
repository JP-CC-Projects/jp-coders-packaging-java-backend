package com.coderscampus.coderpackagingjavabackend.repository;

import com.coderscampus.coderpackagingjavabackend.domain.CheckIn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CheckInRepository extends JpaRepository<CheckIn, Long> {
    @Query("SELECT c FROM CheckIn c WHERE c.student.id = :studentId AND c.checkOutTime IS NULL")
    Optional<CheckIn> findByStudentIdAndCheckOutTimeIsNull(Long studentId);
}