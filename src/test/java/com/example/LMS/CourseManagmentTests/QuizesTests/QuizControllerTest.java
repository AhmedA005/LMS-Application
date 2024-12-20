package com.example.LMS.CourseManagmentTests.QuizesTests;

import com.example.LMS.CourseManagement.Course.Course;
import com.example.LMS.CourseManagement.Question.MCQQuestion;
import com.example.LMS.CourseManagement.Question.Question;
import com.example.LMS.CourseManagement.Quiz.Quiz;
import com.example.LMS.CourseManagement.Quiz.QuizController;
import com.example.LMS.CourseManagement.Quiz.QuizService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class QuizControllerTest {

    @InjectMocks
    private QuizController quizController;

    @Mock
    private QuizService quizService;

    @Test
    public void getAllQuizzes() {
        List<Quiz> quizzes = Arrays.asList(
                new Quiz(1L, "Quiz 1", new Date(), Quiz.QuizType.MCQ, 10, null, null, null),
                new Quiz(2L, "Quiz 2", new Date(), Quiz.QuizType.TrueFalse, 5, null, null, null)
        );

        when(quizService.getAllQuizzes()).thenReturn(quizzes);

        List<Quiz> result = quizController.getAllQuizzes();

        assertEquals(2, result.size());
        assertEquals(quizzes, result);
    }

    @Test
    public void getQuizById_Found() {
        Quiz quiz = new Quiz(1L, "Quiz 1", new Date(), Quiz.QuizType.MCQ, 10, null, null, null);
        MCQQuestion question = new MCQQuestion(1L, "What is the capital of France?", Arrays.asList("London", "Paris", "Berlin"));
        quiz.setQuestions(Arrays.asList(question));

        when(quizService.getQuizById(1L)).thenReturn(Optional.of(quiz));

        ResponseEntity<Quiz> response = quizController.getQuizById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(quiz, response.getBody());
        // Assert on the content of the quiz's questions
        assertNotNull(response.getBody().getQuestions());
        assertEquals(1, response.getBody().getQuestions().size());
        assertEquals(question, response.getBody().getQuestions().get(0));
    }

    @Test
    public void getQuizById_NotFound() {
        when(quizService.getQuizById(100L)).thenReturn(Optional.empty());

        ResponseEntity<Quiz> response = quizController.getQuizById(100L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void createQuiz() {
        Quiz quiz = new Quiz(1L, "Quiz 1", new Date(), Quiz.QuizType.MCQ, 10, null, null, null);

        when(quizService.createQuiz(quiz)).thenReturn(quiz);

        Quiz result = quizController.createQuiz(quiz);

        assertEquals(quiz, result);
    }

    @Test
    public void deleteQuiz() {
        // Arrange
        Long quizId = 1L;

        // Act
        quizController.deleteQuiz(quizId);

        // Assert
        Mockito.verify(quizService).deleteQuiz(quizId);
    }
}