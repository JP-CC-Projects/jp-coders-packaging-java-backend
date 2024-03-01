package com.coderscampus.coderpackagingjavabackend.domain;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

public class CheckInListener {
    @PrePersist
    @PreUpdate
    public void beforeSave(CheckIn checkIn) {
        checkIn.calculateTimeInClass();
    }
}