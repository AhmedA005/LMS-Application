package com.example.LMS.PerformanceTracking;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JoinColumn(name = "student_id" , nullable = false)
    private Long studentId;
    @JoinColumn(name = "lesson_id", nullable = false) // Maps to the course's primary key
    private Long lessonId; // Each Attendance is associated with one course

}
