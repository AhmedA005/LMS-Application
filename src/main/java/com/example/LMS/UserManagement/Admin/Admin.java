package com.example.LMS.UserManagement.Admin;

import com.example.LMS.Authentication.UserInfo;
import com.example.LMS.UserManagement.Role;
import com.example.LMS.UserManagement.User;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.*;

@Entity
//@DiscriminatorValue("ADMIN")
@NoArgsConstructor
public class Admin extends UserInfo {
    public Admin(String name, String password, String email, Role role) {
        super();
        setName(name);
        setPassword(password);
        setEmail(email);
        setRole(role);
    }
}
