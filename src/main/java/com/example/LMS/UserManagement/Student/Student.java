package com.example.LMS.UserManagement.Student;

import com.example.LMS.UserManagement.User;
import jakarta.persistence.Entity;


public class Student extends User {

    public Student(Long id, String name, String password, int age, String email) {
        super(id, name, password, age, email);
    }
}

