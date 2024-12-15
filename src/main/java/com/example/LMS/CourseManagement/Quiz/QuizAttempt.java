package com.example.LMS.CourseManagement.Quiz;

import com.example.LMS.UserManagement.Student.Student;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class QuizAttempt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date attemptDate;
    private Double score;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ElementCollection
    private List<Long> selectedQuestionIds; // Stores IDs of randomly selected questions
    @ElementCollection
    private List<String> studentAnswers; // Stores student answers


}
