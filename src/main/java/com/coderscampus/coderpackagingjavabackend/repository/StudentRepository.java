package com.coderscampus.coderpackagingjavabackend.repository;

import com.coderscampus.coderpackagingjavabackend.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}