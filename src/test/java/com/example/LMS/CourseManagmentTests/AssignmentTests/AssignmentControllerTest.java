package com.example.LMS.CourseManagmentTests.AssignmentTests;
import com.example.LMS.CourseManagement.Assignment.Assignment;
import com.example.LMS.CourseManagement.Assignment.AssignmentController;
import com.example.LMS.CourseManagement.Assignment.AssignmentService;
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
public class AssignmentControllerTest {

    @Mock
    private AssignmentService assignmentService;

    @InjectMocks
    private AssignmentController assignmentController;

    private List<Assignment> testAssignments;
    private Assignment testAssignment;

    @BeforeEach
    void setUp() {
        testAssignments = new ArrayList<>();
        testAssignment = new Assignment();
        testAssignment.setId(1L);
        testAssignment.setTitle("Test Assignment");
        testAssignments.add(testAssignment);
    }

    @Test
    void getAllAssignments_ReturnsAllAssignments() {
        when(assignmentService.getAllAssignments()).thenReturn(testAssignments);

        List<Assignment> result = assignmentController.getAllAssignments();

        assertEquals(testAssignments, result);
        verify(assignmentService).getAllAssignments();
    }

    @Test
    void getAssignmentById_ExistingId_ReturnsAssignment() {
        when(assignmentService.getAssignmentById(1L)).thenReturn(Optional.of(testAssignment));

        ResponseEntity<Assignment> response = assignmentController.getAssignmentById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testAssignment, response.getBody());
        verify(assignmentService).getAssignmentById(1L);
    }

    @Test
    void getAssignmentById_NonExistingId_ReturnsNotFound() {
        when(assignmentService.getAssignmentById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Assignment> response = assignmentController.getAssignmentById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(assignmentService).getAssignmentById(1L);
    }

    @Test
    void createAssignment_CreatesAndReturnsAssignment() {
        when(assignmentService.createAssignment(testAssignment)).thenReturn(testAssignment);

        Assignment result = assignmentController.createAssignment(testAssignment);

        assertEquals(testAssignment, result);
        verify(assignmentService).createAssignment(testAssignment);
    }

    @Test
    void updateAssignment_ExistingId_UpdatesAndReturnsAssignment() {
        when(assignmentService.updateAssignment(1L, testAssignment)).thenReturn(testAssignment);

        ResponseEntity<Assignment> response = assignmentController.updateAssignment(1L, testAssignment);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testAssignment, response.getBody());
        verify(assignmentService).updateAssignment(1L, testAssignment);
    }

    @Test
    void updateAssignment_NonExistingId_ReturnsNotFound() {
        when(assignmentService.updateAssignment(1L, testAssignment)).thenThrow(new RuntimeException("Assignment not found"));

        ResponseEntity<Assignment> response = assignmentController.updateAssignment(1L, testAssignment);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(assignmentService).updateAssignment(1L, testAssignment);
    }

    @Test
    void deleteAssignment_ExistingId_DeletesAssignment() {
        ResponseEntity<Void> response = assignmentController.deleteAssignment(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(assignmentService).deleteAssignment(1L);
    }
}