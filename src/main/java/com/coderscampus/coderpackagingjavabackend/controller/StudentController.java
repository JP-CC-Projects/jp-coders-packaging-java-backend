package com.coderscampus.coderpackagingjavabackend.controller;
import com.coderscampus.coderpackagingjavabackend.domain.Student;
import com.coderscampus.coderpackagingjavabackend.services.StudentService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@ComponentScan
@RestController
@RequestMapping("/api/student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }
    @GetMapping("/get-one-student")
    public ResponseEntity<Student> getOneStudentById(
            @RequestParam Long studentId) {
        Student requestedStudent = studentService.getOneStudentById(studentId);
        return ResponseEntity.ok(requestedStudent);
    }
    @GetMapping("/get-featured-students")
    public ResponseEntity<List<Student>> getFeaturedStudentList() {
        List<Student> featuredStudentList = studentService.getFeaturedStudents();
        return ResponseEntity.ok(featuredStudentList);
    }
}
