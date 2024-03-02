package com.coderscampus.coderpackagingjavabackend.services;

import com.coderscampus.coderpackagingjavabackend.domain.CheckIn;
import com.coderscampus.coderpackagingjavabackend.domain.Student;
import com.coderscampus.coderpackagingjavabackend.repository.StudentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    private final ObjectMapper objectMapper;
    private final StudentRepository studentRepository;

    public StudentService(ObjectMapper objectMapper,
                          StudentRepository studentRepository) {
        this.objectMapper = objectMapper;
        this.studentRepository = studentRepository;
    }

    public Student getOneStudentById(Long studentId) {
        return studentRepository.findById(studentId).orElseThrow(() -> new EntityNotFoundException(
                "Student not found with id: " + studentId));
    }

    public List<Student> getFeaturedStudents() {
        //  1. Set criteria for "featured" (currently enrolled and w/o a job?), return 5 students
        //  2. Go further and create a FeaturedStudents DTO that only returns what is needed
        //  (i.e. student id, image, email, linkedin, data collected, etc)
        return null;
    }

    public Student createStudent(Student createStudentRequest) {
        Student newStudent = new Student();
        newStudent.setFirstName(createStudentRequest.getFirstName());
        newStudent.setLastName(createStudentRequest.getLastName());
        newStudent.setEmail(createStudentRequest.getEmail());
        studentRepository.save(newStudent);
        return newStudent;
    }

    public Student updateStudentPhoto(Long studentId, byte[] photoData) {
        Student existingStudent = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Check-in not found for student with id: " + studentId));
        existingStudent.setStudentImage(photoData);
        studentRepository.save(existingStudent);
        return existingStudent;
    }
}
