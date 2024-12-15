package com.example.LMS.CourseManagement.Quiz;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
    // Find all quizzes associated with a specific course
    List<Quiz> findByCourseId(Long courseId);
}
