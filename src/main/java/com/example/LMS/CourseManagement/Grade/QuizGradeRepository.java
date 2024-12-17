package com.example.LMS.CourseManagement.Grade;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizGradeRepository extends JpaRepository<QuizGrade, Long> {
    // Find all grades for a specific student
    List<QuizGrade> findByStudentId(Long studentId);

    // Find all grades for a specific quiz attempt
    List<QuizGrade> findByQuizAttemptId(Long quizAttemptId);
}
