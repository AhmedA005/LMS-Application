package com.example.LMS.UserManagement.Admin;

import com.example.LMS.UserManagement.Student.Student;
import com.example.LMS.UserManagement.Student.StudentRepository;
import com.example.LMS.UserManagement.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    AdminRepository adminRepository;
    StudentRepository studentRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    void addStudent(Student user) {
        studentRepository.save(user);
    }
}
