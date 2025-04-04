package com.example.s380f_gp_xd.repository;

import com.example.s380f_gp_xd.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    // Find by student ID
    Optional<Student> findByStudentId(String studentId);

    // Find all students in a department
    List<Student> findByDepartment(Student.Department department);

    // Find students by year of study
    List<Student> findByYearOfStudy(Integer yearOfStudy);

    // Check if student ID exists
    boolean existsByStudentId(String studentId);
}