package com.example.LMS.UserManagementTests.InstructorTests;
import com.example.LMS.CourseManagement.Assignment.Assignment;
import com.example.LMS.CourseManagement.Course.Course;
import com.example.LMS.CourseManagement.Grade.AssignmentGrade;
import com.example.LMS.CourseManagement.Lesson.Lesson;
import com.example.LMS.PermissionDeniedException;
import com.example.LMS.UserManagement.Instructor.InstructorController;
import com.example.LMS.UserManagement.Instructor.InstructorService;
import com.example.LMS.UserManagement.Student.Student;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InstructorControllerTest {

    @Mock
    private InstructorService instructorService;

    @InjectMocks
    private InstructorController instructorController;

    private Course course;
    private Lesson lesson;
    private Assignment assignment;
    private AssignmentGrade grade;
    private Student student;
    private Long instructorId;
    private Long courseId;
    private Long studentId;

    @BeforeEach
    void setUp() {
        course = new Course();
        course.setTitle("Test Course");
        lesson = new Lesson();
        assignment = new Assignment();
        grade = new AssignmentGrade();
        student = new Student();

        instructorId = 1L;
        courseId = 1L;
        studentId = 1L;
    }

    @Test
    void createCourse_shouldCreateCourseAndReturnSuccessMessage() {
        ResponseEntity<String> response = instructorController.createCourse(course);
        verify(instructorService, times(1)).createCourse(course);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Course " + course.getTitle() + " Added Successfully", response.getBody());
    }

    @Test
    void addLesson_shouldAddLessonAndReturnSuccess() throws PermissionDeniedException {
        doNothing().when(instructorService).addLesson(instructorId, lesson); // Corrected
        ResponseEntity<String> response = instructorController.addLesson(instructorId, lesson);
        verify(instructorService, times(1)).addLesson(instructorId, lesson);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Lesson added successfully", response.getBody());
    }

    @Test
    void addLesson_shouldReturnForbiddenOnPermissionDenied() throws PermissionDeniedException {
        doThrow(new PermissionDeniedException("No permission")).when(instructorService).addLesson(instructorId, lesson); // Corrected
        ResponseEntity<String> response = instructorController.addLesson(instructorId, lesson);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("No permission", response.getBody());
    }

    @Test
    void addLesson_shouldReturnInternalServerErrorOnException() throws PermissionDeniedException {
        doThrow(new RuntimeException("Some error")).when(instructorService).addLesson(instructorId, lesson); // Corrected
        ResponseEntity<String> response = instructorController.addLesson(instructorId, lesson);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("You do not have permission to add lessons to this course.", response.getBody());
    }

    @Test
    void removeLesson_shouldRemoveLessonAndReturnSuccess() throws PermissionDeniedException{
        doNothing().when(instructorService).removeLesson(instructorId, lesson);
        ResponseEntity<String> response = instructorController.removeLesson(instructorId, lesson);
        verify(instructorService, times(1)).removeLesson(instructorId, lesson);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Lesson deleted successfully", response.getBody());
    }

    @Test
    void removeLesson_shouldReturnForbiddenOnPermissionDenied() throws PermissionDeniedException{
        doThrow(new PermissionDeniedException("No permission")).when(instructorService).removeLesson(instructorId, lesson);
        ResponseEntity<String> response = instructorController.removeLesson(instructorId, lesson);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("No permission", response.getBody());
    }

    @Test
    void addAssignment_shouldAddAssignmentAndReturnSuccess() throws PermissionDeniedException {
        doNothing().when(instructorService).addAssignment(instructorId, assignment);
        ResponseEntity<String> response = instructorController.addAssignment(instructorId, assignment);
        verify(instructorService, times(1)).addAssignment(instructorId, assignment);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Assignment added successfully", response.getBody());
    }

    @Test
    void addAssignment_shouldReturnForbiddenOnPermissionDenied() throws PermissionDeniedException {
        doThrow(new PermissionDeniedException("No permission")).when(instructorService).addAssignment(instructorId, assignment);
        ResponseEntity<String> response = instructorController.addAssignment(instructorId, assignment);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("No permission", response.getBody());
    }

    @Test
    void removeAssignment_shouldRemoveAssignmentAndReturnSuccess() throws PermissionDeniedException{
        doNothing().when(instructorService).removeAssignment(instructorId, assignment);
        ResponseEntity<String> response = instructorController.removeAssignment(instructorId, assignment);
        verify(instructorService, times(1)).removeAssignment(instructorId, assignment);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Assignment deleted successfully", response.getBody());
    }

    @Test
    void removeAssignment_shouldReturnForbiddenOnPermissionDenied() throws PermissionDeniedException{
        doThrow(new PermissionDeniedException("No permission")).when(instructorService).removeAssignment(instructorId, assignment);
        ResponseEntity<String> response = instructorController.removeAssignment(instructorId, assignment);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("No permission", response.getBody());
    }

    @Test
    void addGrade_shouldAddGradeAndReturnSuccess() throws PermissionDeniedException {
        doNothing().when(instructorService).addAssignmentGrade(instructorId, grade);
        ResponseEntity<String> response = instructorController.addGrade(instructorId, grade);
        verify(instructorService, times(1)).addAssignmentGrade(instructorId, grade);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Grade added successfully", response.getBody());
    }

    @Test
    void addGrade_shouldReturnForbiddenOnPermissionDenied() throws PermissionDeniedException {
        doThrow(new PermissionDeniedException("No permission")).when(instructorService).addAssignmentGrade(instructorId, grade);
        ResponseEntity<String> response = instructorController.addGrade(instructorId, grade);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("No permission", response.getBody());
    }

    @Test
    void getEnrolledStudents_shouldReturnEnrolledStudents() {
        List<Student> enrolledStudents = new ArrayList<>();
        enrolledStudents.add(student);
        when(instructorService.getEnrolledStudents(courseId)).thenReturn(enrolledStudents);

        ResponseEntity<List<Student>> response = instructorController.getEnrolledStudents(courseId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(enrolledStudents, response.getBody());
    }

    @Test
    void removeStudentFromCourse_shouldRemoveStudentAndReturnSuccess() throws PermissionDeniedException {
        doNothing().when(instructorService).removeStudentFromCourse(instructorId,courseId,studentId);
        ResponseEntity<String> response = instructorController.removeStudentFromCourse(instructorId,courseId,studentId);
        verify(instructorService, times(1)).removeStudentFromCourse(instructorId,courseId,studentId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Student removed from course successfully", response.getBody());
    }

    @Test
    void removeStudentFromCourse_shouldReturnForbiddenOnPermissionDenied() throws PermissionDeniedException {
        doThrow(new PermissionDeniedException("No permission")).when(instructorService).removeStudentFromCourse(instructorId,courseId,studentId);
        ResponseEntity<String> response = instructorController.removeStudentFromCourse(instructorId,courseId,studentId);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("No permission", response.getBody());
    }
    @Test
        void removeStudentFromCourse_shouldReturnNotFoundOnIllegalArgumentException() throws PermissionDeniedException {
            doThrow(new IllegalArgumentException("Student not found")).when(instructorService).removeStudentFromCourse(instructorId, courseId, studentId);
            ResponseEntity<String> response = instructorController.removeStudentFromCourse(instructorId, courseId, studentId);
            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertEquals("Student not found", response.getBody());
        }

        @Test
        void removeStudentFromCourse_shouldReturnInternalServerErrorOnException() throws PermissionDeniedException {
            doThrow(new RuntimeException("Some error")).when(instructorService).removeStudentFromCourse(instructorId, courseId, studentId);
            ResponseEntity<String> response = instructorController.removeStudentFromCourse(instructorId, courseId, studentId);
            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
            assertEquals("An unexpected error occurred", response.getBody());
        }










}