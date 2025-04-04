package com.example.s380f_gp_xd.controller;

import com.example.s380f_gp_xd.entity.Student;
import com.example.s380f_gp_xd.service.RegistrationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/registration")
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/student")
    public Student registerStudent(@RequestBody Student student) {
        return registrationService.registerStudent(student);
    }
}