package com.coderscampus.coderpackagingjavabackend.repository;

import com.coderscampus.coderpackagingjavabackend.domain.CheckIn;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckInRepository extends JpaRepository<CheckIn, Long> {
}