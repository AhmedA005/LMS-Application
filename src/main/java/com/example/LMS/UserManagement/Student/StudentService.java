package com.example.LMS.UserManagement.Student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
    StudentRepository studentRepository;


    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

//    public void enrollCourse(Course course){
//        List<Course> prerequisites = course.getPre();
//
//    }
}
