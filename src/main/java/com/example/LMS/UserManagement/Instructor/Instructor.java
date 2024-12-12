package com.example.LMS.UserManagement.Instructor;

import com.example.LMS.UserManagement.Role;
import com.example.LMS.UserManagement.User;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Instructor extends User {
    public Instructor(Long id, String name, String password, int age, String email, Role role) {
        super(id, name, password, age, email, role);
    }
}
