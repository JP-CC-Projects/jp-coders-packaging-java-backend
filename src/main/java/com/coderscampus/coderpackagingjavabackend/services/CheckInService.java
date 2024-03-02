package com.coderscampus.coderpackagingjavabackend.services;

import com.coderscampus.coderpackagingjavabackend.domain.CheckIn;
import com.coderscampus.coderpackagingjavabackend.domain.Student;
import com.coderscampus.coderpackagingjavabackend.repository.CheckInRepository;
import com.coderscampus.coderpackagingjavabackend.repository.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class CheckInService {
    private final CheckInRepository checkInRepository;
    private final StudentRepository studentRepository;

    public CheckInService(CheckInRepository checkInRepository,
                          StudentRepository studentRepository) {
        this.checkInRepository = checkInRepository;
        this.studentRepository = studentRepository;
    }

    public CheckIn createStudentCheckIn(Long studentId) {
        CheckIn checkIn = new CheckIn();
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + studentId));
        checkIn.setStudent(student);
        checkIn.setCheckInTime(Instant.now());
        checkInRepository.save(checkIn);
        return checkIn;
    }

    public CheckIn endStudentCheckIn(Long studentId) {
        CheckIn checkIn = checkInRepository.findByStudentIdAndCheckOutTimeIsNull(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Check-in not found for student with id: " + studentId));
        checkIn.setCheckOutTime(Instant.now());
        checkInRepository.save(checkIn);
        return checkIn;
    }
}
