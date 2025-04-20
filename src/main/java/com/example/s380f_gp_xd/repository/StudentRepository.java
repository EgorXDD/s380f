package com.example.s380f_gp_xd.repository;

import com.example.s380f_gp_xd.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {


    Optional<Student> findByStudentId(String studentId);


    List<Student> findByDepartment(Student.Department department);


    List<Student> findByYearOfStudy(Integer yearOfStudy);


    boolean existsByStudentId(String studentId);
}