package com.coderscampus.coderpackagingjavabackend.controller;

import com.coderscampus.coderpackagingjavabackend.domain.CheckIn;
import com.coderscampus.coderpackagingjavabackend.domain.Student;
import com.coderscampus.coderpackagingjavabackend.services.CheckInService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@ComponentScan
@RestController
@RequestMapping("/api/check-in")

public class CheckInController {
    private final CheckInService checkInService;

    public CheckInController(CheckInService checkInService) {
        this.checkInService = checkInService;
    }
    @PostMapping("/create-check-in")
    public ResponseEntity<CheckIn> createStudentCheckIn(
            @RequestParam Long studentId) {
        CheckIn newCheckIn = checkInService.createStudentCheckIn(studentId);
        return ResponseEntity.ok(newCheckIn);
    }
}