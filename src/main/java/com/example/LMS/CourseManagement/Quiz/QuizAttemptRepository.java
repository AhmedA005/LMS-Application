package com.example.LMS.CourseManagement.Quiz;

import com.example.LMS.UserManagement.Student.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizAttemptRepository extends JpaRepository<QuizAttempt, Long> {
    QuizAttempt findByQuizAndStudent(Quiz quiz, Student student);
}
