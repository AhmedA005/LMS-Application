package com.example.LMS.CourseManagement.Grade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quiz-grades")
public class QuizGradeController {

    @Autowired
    private QuizGradeService quizGradeService;

    @GetMapping
    public List<QuizGrade> getAllQuizGrades() {
        return quizGradeService.getAllQuizGrades();
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuizGrade> getQuizGradeById(@PathVariable Long id) {
        return quizGradeService.getQuizGradeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public QuizGrade createQuizGrade(@RequestBody QuizGrade quizGrade) {
        return quizGradeService.createQuizGrade(quizGrade);
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuizGrade> updateQuizGrade(@PathVariable Long id, @RequestBody QuizGrade updatedQuizGrade) {
        try {
            QuizGrade updated = quizGradeService.updateQuizGrade(id, updatedQuizGrade);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuizGrade(@PathVariable Long id) {
        quizGradeService.deleteQuizGrade(id);
        return ResponseEntity.noContent().build();
    }
}
