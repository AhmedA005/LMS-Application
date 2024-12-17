package com.example.LMS.CourseManagement.Question;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TrueFalseQuestionRepository extends JpaRepository<TrueFalseQuestion, Long> {
    List<Question> findByCourseId(Long courseId);
}
