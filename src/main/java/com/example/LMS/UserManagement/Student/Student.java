package com.example.LMS.UserManagement.Student;

import com.example.LMS.Authentication.UserInfo;
import com.example.LMS.UserManagement.Role;
import com.example.LMS.UserManagement.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorValue("STUDENT")
@NoArgsConstructor
public class Student extends UserInfo {
    public Student(String name, String password, String email, Role role) {
        super();
        setName(name);
        setPassword(password);
        setEmail(email);
        setRole(role);
    }
}
