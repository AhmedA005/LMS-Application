package com.example.LMS.CourseManagement.Course;

import com.example.LMS.CourseManagement.Assignment.Assignment;
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
    @JoinColumn(name = "instructor_id", nullable = false)
    private Instructor instructor; // Each course belongs to one instructor

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Lesson> lessons; // A course has multiple lessons

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL) // Use the field name from Assignment
    private List<Assignment> assignments;

}
