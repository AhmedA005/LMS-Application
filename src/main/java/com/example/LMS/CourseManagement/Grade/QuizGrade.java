package com.example.LMS.CourseManagement.Grade;

import com.example.LMS.CourseManagement.Quiz.QuizAttempt;
import com.example.LMS.UserManagement.Student.Student;
import jakarta.persistence.*;
import java.util.Date;

@Entity
public class QuizGrade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "quiz_attempt_id", nullable = false)
    private QuizAttempt quizAttempt;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    private Double score;
    private String feedback;

    @Temporal(TemporalType.TIMESTAMP)
    private Date gradedAt;

    public QuizGrade() {
        // Default constructor
    }

    public QuizGrade(QuizAttempt quizAttempt, Student student, Double score, String feedback, Date gradedAt) {
        this.quizAttempt = quizAttempt;
        this.student = student;
        this.score = score;
        this.feedback = feedback;
        this.gradedAt = gradedAt;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public QuizAttempt getQuizAttempt() {
        return quizAttempt;
    }

    public void setQuizAttempt(QuizAttempt quizAttempt) {
        this.quizAttempt = quizAttempt;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public Date getGradedAt() {
        return gradedAt;
    }

    public void setGradedAt(Date gradedAt) {
        this.gradedAt = gradedAt;
    }
}
