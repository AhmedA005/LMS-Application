package com.example.LMS.CourseManagement.Enrollment;

import com.example.LMS.CourseManagement.Course.Course;
import com.example.LMS.UserManagement.Student.Student;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Course course;

    @ManyToOne
    private Student student;
}
