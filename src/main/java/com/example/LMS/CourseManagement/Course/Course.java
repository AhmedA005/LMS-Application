package com.example.LMS.CourseManagement.Course;

import com.example.LMS.CourseManagement.Lesson.Lesson;
import com.example.LMS.UserManagement.Instructor.Instructor;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private int duration; // Duration in weeks

    @ManyToOne
    private Instructor instructor; // Each course belongs to one instructor

    @OneToMany(mappedBy = "courseId", cascade = CascadeType.ALL)
    private List<Lesson> lessons; // A course has multiple lessons
}
