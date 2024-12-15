package com.example.LMS.CourseManagement.Grade;

import com.example.LMS.CourseManagement.Grade.QuizGrade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Service
public class QuizGradeService {
    @Autowired
    private QuizGradeRepository quizGradeRepository;

    public List<QuizGrade> getAllQuizGrades() {
        return quizGradeRepository.findAll();
    }

    public Optional<QuizGrade> getQuizGradeById(Long id) {
        return quizGradeRepository.findById(id);
    }

    public QuizGrade createQuizGrade(QuizGrade quizGrade) {
        return quizGradeRepository.save(quizGrade);
    }

    public QuizGrade updateQuizGrade(Long id, QuizGrade updatedQuizGrade) {
        QuizGrade existingQuizGrade = quizGradeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quiz Grade not found"));
        existingQuizGrade.setScore(updatedQuizGrade.getScore());
        existingQuizGrade.setFeedback(updatedQuizGrade.getFeedback());
        existingQuizGrade.setGradedAt(updatedQuizGrade.getGradedAt());
        return quizGradeRepository.save(existingQuizGrade);
    }

    public void deleteQuizGrade(Long id) {
        quizGradeRepository.deleteById(id);
    }
}
