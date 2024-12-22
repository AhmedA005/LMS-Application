package com.example.LMS.UserManagement.Instructor;

import com.example.LMS.Authentication.UserInfo;
import com.example.LMS.UserManagement.Role;
import com.example.LMS.UserManagement.User;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("INSTRUCTOR")
@NoArgsConstructor
public class Instructor extends UserInfo {
    public Instructor(String name, String password, String email, Role role) {
        super();
        setName(name);
        setPassword(password);
        setEmail(email);
        setRole(role);
    }
}
