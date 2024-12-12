package com.example.LMS.CourseManagement.Enrollment;

import com.example.LMS.UserManagement.Student.Student;
import com.example.LMS.CourseManagement.Course.Course;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EnrollmentRequest {
    private Course course;
    private Student student;
}
