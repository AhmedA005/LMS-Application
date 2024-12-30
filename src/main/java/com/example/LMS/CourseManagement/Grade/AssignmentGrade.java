package com.example.LMS.CourseManagement.Grade;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AssignmentGrade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JoinColumn(name = "student_id" , nullable = false)
    private Long studentId;
    @JoinColumn(name = "assignment_id", nullable = false) // Maps to the course's primary key
    private Long assignmentId; // Each grade is associated with one course
    //Can be enrollmentId
    private String title;
    private Long grade;

//    @ManyToOne
//    private Course course; // Each lesson belongs to one course
}
