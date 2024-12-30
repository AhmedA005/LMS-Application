package com.example.LMS.CourseManagmentTests.GradeTests;

import com.example.LMS.CourseManagement.Grade.AssignmentGradeController;
import com.example.LMS.CourseManagement.Grade.AssignmentGrade;
import com.example.LMS.CourseManagement.Grade.AssignmentGradeService;
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
public class AssignmentGradeControllerTest {

    @Mock
    private AssignmentGradeService assignmentGradeService;

    @InjectMocks
    private AssignmentGradeController assignmentGradeController;

    private List<AssignmentGrade> testGrades;
    private AssignmentGrade testGrade;

    @BeforeEach
    void setUp() {
        testGrades = new ArrayList<>();
        testGrade = new AssignmentGrade();
        testGrade.setId(1L);
        testGrade.setGrade(95L); // Example grade
        testGrades.add(testGrade);
    }

    @Test
    void getAllGrades_ReturnsAllGrades() {
        when(assignmentGradeService.getAllGrades()).thenReturn(testGrades);

        List<AssignmentGrade> result = assignmentGradeController.getAllGrades();

        assertEquals(testGrades, result);
        verify(assignmentGradeService).getAllGrades();
    }

    @Test
    void getGradeById_ExistingId_ReturnsGrade() {
        when(assignmentGradeService.getGradeById(1L)).thenReturn(Optional.of(testGrade));

        ResponseEntity<AssignmentGrade> response = assignmentGradeController.getGradeById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testGrade, response.getBody());
        verify(assignmentGradeService).getGradeById(1L);
    }

    @Test
    void getGradeById_NonExistingId_ReturnsNotFound() {
        when(assignmentGradeService.getGradeById(1L)).thenReturn(Optional.empty());

        ResponseEntity<AssignmentGrade> response = assignmentGradeController.getGradeById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(assignmentGradeService).getGradeById(1L);
    }

    @Test
    void createGrade_CreatesAndReturnsGrade() {
        when(assignmentGradeService.createGrade(testGrade)).thenReturn(testGrade);

        AssignmentGrade result = assignmentGradeController.createGrade(testGrade);

        assertEquals(testGrade, result);
        verify(assignmentGradeService).createGrade(testGrade);
    }

    @Test
    void updateGrade_ExistingId_UpdatesAndReturnsGrade() {
        when(assignmentGradeService.updateGrade(1L, testGrade)).thenReturn(testGrade);

        ResponseEntity<AssignmentGrade> response = assignmentGradeController.updateGrade(1L, testGrade);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testGrade, response.getBody());
        verify(assignmentGradeService).updateGrade(1L, testGrade);
    }

    @Test
    void updateGrade_NonExistingId_ReturnsNotFound() {
        when(assignmentGradeService.updateGrade(1L, testGrade)).thenThrow(new RuntimeException("Grade not found"));

        ResponseEntity<AssignmentGrade> response = assignmentGradeController.updateGrade(1L, testGrade);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(assignmentGradeService).updateGrade(1L, testGrade);
    }

    @Test
    void deleteGrade_ExistingId_DeletesGrade() {
        ResponseEntity<Void> response = assignmentGradeController.deleteGrade(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(assignmentGradeService).deleteGrade(1L);
    }
}