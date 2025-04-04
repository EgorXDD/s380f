package com.example.s380f_gp_xd.controller;

import com.example.s380f_gp_xd.entity.User;
import com.example.s380f_gp_xd.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
@Controller
public class Login {

    private final UserService userService;

    public Login(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String showLoginForm(@RequestParam(required = false) String error, Model model) {
        if (error != null && error.equals("true")) {
            model.addAttribute("errorMessage", "Invalid username or password. Please try again.");
        }
        return "login";
    }



    @PostMapping("/process-login")
    public String processLogin(@RequestParam String username, @RequestParam String password, Model model) {
        User user = userService.authenticate(username, password);

        if (user == null) {
            model.addAttribute("errorMessage", "Invalid username or password. Please try again.");
            return "login";
        }

        return "redirect:/welcome";
    }
}
