package com.example.LMS.CourseManagement.Lesson;


import com.example.LMS.CourseManagement.Course.Course;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false) // Maps to the course's primary key
    @JsonBackReference
    private Course course; // Each lesson belongs to one course
    private String title;
    private String otp; // OTP for attendance

//    @ManyToOne
//    private Course course; // Each lesson belongs to one course
}
