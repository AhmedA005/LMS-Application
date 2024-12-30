package com.example.LMS.CourseManagmentTests.QuizesTests;

import com.example.LMS.CourseManagement.Quiz.QuizController;
import com.example.LMS.CourseManagement.Quiz.QuizService;
import com.example.LMS.CourseManagement.Quiz.Quiz;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class QuizControllerTest {

    @Mock
    private QuizService quizService;

    @InjectMocks
    private QuizController quizController;

    private List<Quiz> testQuizzes;
    private Quiz testQuiz;

    @BeforeEach
    void setUp() {
        testQuizzes = new ArrayList<>();
        testQuiz = new Quiz();
        testQuiz.setId(1L);
        testQuiz.setTitle("Test Quiz");
        testQuizzes.add(testQuiz);
    }

    @Test
    void getAllQuizzes_ReturnsAllQuizzes() {
        when(quizService.getAllQuizzes()).thenReturn(testQuizzes);

        List<Quiz> result = quizController.getAllQuizzes();

        assertEquals(testQuizzes, result);
        verify(quizService).getAllQuizzes();
    }

    @Test
    void getQuizById_ExistingId_ReturnsQuiz() {
        when(quizService.getQuizById(1L)).thenReturn(Optional.of(testQuiz));

        ResponseEntity<Quiz> response = quizController.getQuizById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testQuiz, response.getBody());
        verify(quizService).getQuizById(1L);
    }

    @Test
    void getQuizById_NonExistingId_ReturnsNotFound() {
        when(quizService.getQuizById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Quiz> response = quizController.getQuizById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(quizService).getQuizById(1L);
    }

    @Test
    void createQuiz_CreatesAndReturnsQuiz() {
        when(quizService.createQuiz(testQuiz)).thenReturn(testQuiz);

        Quiz result = quizController.createQuiz(testQuiz);

        assertEquals(testQuiz, result);
        verify(quizService).createQuiz(testQuiz);
    }

    @Test
    void updateQuiz_ExistingId_UpdatesAndReturnsQuiz() {
        when(quizService.updateQuiz(1L, testQuiz)).thenReturn(testQuiz);

        ResponseEntity<Quiz> response = quizController.updateQuiz(1L, testQuiz);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testQuiz, response.getBody());
        verify(quizService).updateQuiz(1L, testQuiz);
    }

    @Test
    void updateQuiz_NonExistingId_ReturnsNotFound() {
        when(quizService.updateQuiz(1L, testQuiz)).thenThrow(new RuntimeException("Quiz not found")); // Simulate not found

        ResponseEntity<Quiz> response = quizController.updateQuiz(1L, testQuiz);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(quizService).updateQuiz(1L, testQuiz);
    }

    @Test
    void deleteQuiz_DeletesQuiz() {
        ResponseEntity<Void> response = quizController.deleteQuiz(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(quizService).deleteQuiz(1L);
    }
}
