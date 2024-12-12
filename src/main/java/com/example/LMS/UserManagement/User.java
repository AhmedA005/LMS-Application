package com.example.LMS.UserManagement;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public abstract class User {
    @Id
    private Long id;
    private String name;
    private String password;
    private int age;
    private String email;
    Role role;

    public User(Long id, String name, String password, int age, String email, Role role) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.age = age;
        this.email = email;
        this.role = role;
    }

    public User() {

    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
