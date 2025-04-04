package com.example.s380f_gp_xd.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String type;
    private String email;
    private String fullname;
    private String phonenumber;

    public Long getId() {
        return id;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getType() {
        return type;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullname;
    }

    public String getPhoneNumber() {
        return phonenumber;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public void setFullName(String fullName) {
        this.fullname = fullName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phonenumber = phoneNumber;
    }
    public void setType(String type) {
        this.type = type;
    }


}

