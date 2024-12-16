package com.example.LMS.CourseManagement.Assignment;
import com.example.LMS.CourseManagement.Course.Course;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false) // Maps to the course's primary key
    @JsonBackReference
    private Course course; // Each assignment belongs to one course

    private String title;
    private String description;

    @Override
    public String toString() {
        return "title: " + title + "\ndescription: " + description;
    }
}

