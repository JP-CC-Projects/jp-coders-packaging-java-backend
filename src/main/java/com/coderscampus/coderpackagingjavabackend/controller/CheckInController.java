package com.coderscampus.coderpackagingjavabackend.controller;

import com.coderscampus.coderpackagingjavabackend.domain.CheckIn;
import com.coderscampus.coderpackagingjavabackend.services.CheckInService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@ComponentScan
@RestController
@RequestMapping("/api/check-in")

public class CheckInController {
    private final CheckInService checkInService;

    public CheckInController(CheckInService checkInService) {
        this.checkInService = checkInService;
    }
    @PostMapping("/start-check-in")
    public ResponseEntity<CheckIn> createAndStartStudentCheckIn(
            @RequestParam Long studentId) {
        CheckIn newCheckIn = checkInService.createStudentCheckIn(studentId);
        return ResponseEntity.ok(newCheckIn);
    }
    @PostMapping("/finish-check-in")
    public ResponseEntity<CheckIn> finishStudentCheckIn(
            @RequestParam Long studentId) {
        CheckIn finishedCheckIn = checkInService.endStudentCheckIn(studentId);
        return ResponseEntity.ok(finishedCheckIn);
    }
}