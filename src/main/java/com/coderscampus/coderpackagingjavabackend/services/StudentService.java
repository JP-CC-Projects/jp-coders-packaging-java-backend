package com.coderscampus.coderpackagingjavabackend.services;

import com.coderscampus.coderpackagingjavabackend.domain.Student;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    public Student getOneStudentById(Long studentId){
        return null;
    }

    public List<Student> getFeaturedStudents() {
        //  1. Set criteria for "featured" (currently enrolled and w/o a job?), return 5 students
        //  2. Go further and create a FeaturedStudents DTO that only returns what is needed
        //  (i.e. student id, image, email, linkedin, data collected, etc)
        return null;
    }
}
