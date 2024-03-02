package com.coderscampus.coderpackagingjavabackend.services;

import com.coderscampus.coderpackagingjavabackend.domain.CheckIn;
import com.coderscampus.coderpackagingjavabackend.domain.Student;
import com.coderscampus.coderpackagingjavabackend.repository.CheckInRepository;
import com.coderscampus.coderpackagingjavabackend.repository.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public CheckIn createStudentCheckIn(Long studentId) {
//      Prevents accidental opening of multiple check-ins
        if(checkInRepository.findByStudentIdAndCheckOutTimeIsNull(studentId).isPresent()){
            endStudentCheckIn(studentId);
        }
        System.out.println("Creating Checkin for Student with ID: " + studentId);
        CheckIn newCheckIn = new CheckIn();
        Student existingStudent = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + studentId));
        newCheckIn.setStudent(existingStudent);
        newCheckIn.setCheckInTime(Instant.now());
        checkInRepository.save(newCheckIn);
        existingStudent.getCheckIns().add(newCheckIn);
        studentRepository.save(existingStudent);
        return newCheckIn;
    }

    @Transactional
    public CheckIn endStudentCheckIn(Long studentId) {
        System.out.println("Creating CheckOut for Student with ID: " + studentId);
        Student existingStudent = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Student not found with id: " + studentId));
        CheckIn unclosedCheckIn = checkInRepository.findByStudentIdAndCheckOutTimeIsNull(studentId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Unclosed Check-in not found for student with id: " + studentId));
        unclosedCheckIn.setCheckOutTime(Instant.now());
        checkInRepository.save(unclosedCheckIn);
        existingStudent.getCheckIns().add(unclosedCheckIn);
        return unclosedCheckIn;
    }
}
