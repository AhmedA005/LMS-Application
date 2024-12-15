package com.example.LMS.notificationsystemTests;
import com.example.LMS.UserManagement.Instructor.Instructor;
import com.example.LMS.UserManagement.Student.Student;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
@Entity
@Data

public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    private LocalDateTime timestamp;

    @ManyToOne
    private Student student;

    @ManyToOne
    private Instructor instructor;

    private boolean isRead;

    public Notification(String message, Student student, Instructor instructor) {
        this.message = message;
        this.student = student;
        this.instructor = instructor;
        this.timestamp = LocalDateTime.now();
        this.isRead = false;
    }

    public Notification() {

    }
}