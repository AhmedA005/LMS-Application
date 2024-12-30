package com.example.LMS.CourseManagement.Question;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShortAnswerRepository extends JpaRepository<ShortAnswerQuestion, Long> {
    List<Question> findByCourseId(Long courseId);
}
