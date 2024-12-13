package com.example.LMS.CourseManagement.Grade;
import com.example.LMS.CourseManagement.Course.Course;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long studentId;
    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false) // Maps to the course's primary key
    private Course course; // Each grade is associated with one course
    //Can be enrollmentId
    private String title;
    private Long grade;

//    @ManyToOne
//    private Course course; // Each lesson belongs to one course
}
