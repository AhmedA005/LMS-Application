package com.example.LMS.UserManagement.Admin;

import com.example.LMS.UserManagement.Role;
import com.example.LMS.UserManagement.User;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@NoArgsConstructor
public class Admin extends User {
    public Admin(Long id, String name, String password, int age, String email, Role role) {
        super(id, name, password, age, email, role);
    }
}
