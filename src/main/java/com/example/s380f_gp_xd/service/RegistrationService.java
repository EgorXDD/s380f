package com.example.s380f_gp_xd.service;

import com.example.s380f_gp_xd.entity.Student;
import com.example.s380f_gp_xd.repository.StudentRepository;
import com.example.s380f_gp_xd.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistrationService {

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;

    public RegistrationService(StudentRepository studentRepository, UserRepository userRepository) {
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Student registerStudent(Student student) {
        // Check if username or email already exists
        if (userRepository.findByUsername(student.getUsername()) != null) {
            throw new RuntimeException("Username already exists");
        }

        if (userRepository.findByEmail(student.getEmail()) != null) {
            throw new RuntimeException("Email already registered");
        }

        // Generate student ID if not provided
        if (student.getStudentId() == null) {
            student.setStudentId(generateStudentId());
        }

        return studentRepository.save(student);
    }

    private String generateStudentId() {
        // Implement your student ID generation logic
        return "STU" + System.currentTimeMillis() % 10000;
    }
}