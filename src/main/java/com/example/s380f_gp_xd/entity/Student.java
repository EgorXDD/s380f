package com.example.s380f_gp_xd.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "students")
@PrimaryKeyJoinColumn(name = "user_id")
public class Student extends User {

    @Column(unique = true)
    private String studentId;

    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    private Department department;

    private String major;
    private Integer yearOfStudy;

    public Student() {
        this.setType("student"); // Automatically set user type
    }

    // Enum for departments
    public enum Department {
        COMPUTER_SCIENCE,
        ELECTRICAL_ENGINEERING,
        MECHANICAL_ENGINEERIN,
        BUSINESS_ADMINISTRATION
    }

    // Getters and setters
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public Integer getYearOfStudy() {
        return yearOfStudy;
    }

    public void setYearOfStudy(Integer yearOfStudy) {
        this.yearOfStudy = yearOfStudy;
    }
}