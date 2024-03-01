package com.coderscampus.coderpackagingjavabackend.services;

import com.coderscampus.coderpackagingjavabackend.domain.CheckIn;
import com.coderscampus.coderpackagingjavabackend.repository.CheckInRepository;
import com.coderscampus.coderpackagingjavabackend.repository.StudentRepository;
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
        checkIn.setStudent();
        checkIn.setCheckInTime(Instant.now());
        return null;
    }
}
