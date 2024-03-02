package com.coderscampus.coderpackagingjavabackend.domain;

import jakarta.persistence.*;

import java.math.BigInteger;
import java.time.Duration;
import java.time.Instant;

@Entity
@EntityListeners(CheckInListener.class)
@Table(name = "check_in")
public class CheckIn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
    private Instant checkInTime;
    private Instant checkOutTime;
    private BigInteger timeInClassInSeconds;
    private Boolean isCoding = false;
    private Boolean isGuiding = false;

    public CheckIn() {
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Long getId() {
        return id;
    }

    public Instant getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(Instant checkInTime) {
        this.checkInTime = checkInTime;
    }

    public Instant getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(Instant checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public BigInteger getTimeInClassInSeconds() {
        return timeInClassInSeconds;
    }

    public void setTimeInClassInSeconds(BigInteger timeInClassInSeconds) {
        this.timeInClassInSeconds = timeInClassInSeconds;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getCoding() {
        return isCoding;
    }

    public void setCoding(Boolean coding) {
        isCoding = coding;
    }

    public Boolean getGuiding() {
        return isGuiding;
    }

    public void setGuiding(Boolean guiding) {
        isGuiding = guiding;
    }

    public void calculateTimeInClass() {
        if (checkInTime != null && checkOutTime != null) {
            long seconds = Duration.between(checkInTime, checkOutTime).getSeconds();
            setTimeInClassInSeconds(BigInteger.valueOf(seconds));
        } else {
            setTimeInClassInSeconds(BigInteger.ZERO);
        }
    }

}