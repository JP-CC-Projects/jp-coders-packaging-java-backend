package com.coderscampus.coderpackagingjavabackend.controller;
import com.coderscampus.coderpackagingjavabackend.domain.Student;
import com.coderscampus.coderpackagingjavabackend.services.StudentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@ComponentScan
@RestController
@RequestMapping("/api/student")
public class StudentController {
    private final ObjectMapper objectMapper;
    private final StudentService studentService;

    public StudentController(ObjectMapper objectMapper, StudentService studentService) {
        this.objectMapper = objectMapper;
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
    @PostMapping("/create-student")
    public ResponseEntity<Student> createStudent(@RequestBody String rawJson) throws JsonProcessingException {
        System.out.println("Received JSON: " + rawJson);
        // Convert rawJson back to Student object manually for debugging purposes
        Student createStudentRequest = objectMapper.readValue(rawJson, Student.class);
        System.out.println("Create student request email: " + createStudentRequest.getEmail());
        System.out.println("Create student request firstName: " + createStudentRequest.getFirstName());
        System.out.println("Create student request lastName: " + createStudentRequest.getLastName());
        Student newStudent = studentService.createStudent(createStudentRequest);
        return ResponseEntity.ok(newStudent);
    }

//    @PostMapping("/update-student")
//    public ResponseEntity<Student> updateStudent(String updatedStudentJson) throws JsonProcessingException {
//        Student updatedStudent = studentService.updateStudent(updatedStudentJson);
//        return ResponseEntity.ok(updatedStudent);
//    }
}
