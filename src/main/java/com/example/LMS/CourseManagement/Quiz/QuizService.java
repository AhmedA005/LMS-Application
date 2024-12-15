package com.example.LMS.CourseManagement.Quiz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuizService {
    @Autowired
    private QuizRepository quizRepository;
    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

    public Optional<Quiz> getQuizById(Long id) {
        return quizRepository.findById(id);
    }

    public Quiz createQuiz(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    public Quiz updateQuiz(Long id, Quiz updatedQuiz) {
        Quiz existingQuiz = quizRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));
        existingQuiz.setTitle(updatedQuiz.getTitle());
        existingQuiz.setCourse(updatedQuiz.getCourse());
        return quizRepository.save(existingQuiz);
    }

    public void deleteQuiz(Long id) {
        quizRepository.deleteById(id);
    }
    
}
