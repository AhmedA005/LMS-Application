package com.example.LMS.UserManagementTests.StudentTests;

import com.example.LMS.CourseManagement.Course.Course;
import com.example.LMS.CourseManagement.Enrollment.EnrollmentRequest;
import com.example.LMS.CourseManagement.Grade.AssignmentGrade;
import com.example.LMS.PermissionDeniedException;
import com.example.LMS.UserManagement.Student.Student;
import com.example.LMS.UserManagement.Student.StudentController;
import com.example.LMS.UserManagement.Student.StudentService;
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
public class StudentControllerTest {

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    private Student student;
    private Course course;
    private EnrollmentRequest enrollmentRequest;
    private Long studentId;
    private Long lessonId;
    private String otp;

    @BeforeEach
    void setUp() {
        student = new Student();
        student.setId(1L);
        course = new Course();
        course.setId(1L);
        enrollmentRequest = new EnrollmentRequest();
        enrollmentRequest.setCourse(course);
        enrollmentRequest.setStudent(student);
        studentId = 1L;
        lessonId = 1L;
        otp = "123456";

    }

    @Test
    void enrollInCourse_shouldEnrollStudentAndReturnSuccessMessage() {
        String expectedMessage = "Enrolled Successfully";
        when(studentService.enrollInCourse(course, student)).thenReturn(expectedMessage);

        ResponseEntity<String> response = studentController.enrollInCourse(enrollmentRequest);

        verify(studentService, times(1)).enrollInCourse(course, student);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedMessage, response.getBody());
    }


    @Test
    void markAttendance_shouldMarkAttendanceAndReturnSuccessMessage() throws PermissionDeniedException {
        String expectedMessage = "Attendance marked successfully";
        when(studentService.markAttendance(studentId, lessonId, otp)).thenReturn(expectedMessage);

        ResponseEntity<String> response = studentController.markAttendance(studentId, lessonId, otp);

        verify(studentService, times(1)).markAttendance(studentId, lessonId, otp);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedMessage, response.getBody());
    }

    @Test
    void markAttendance_shouldReturnIllegalArgumentException() throws PermissionDeniedException{

        when(studentService.markAttendance(studentId, lessonId, otp)).thenThrow(new IllegalArgumentException("Invalid Lesson"));

        ResponseEntity<String> response = studentController.markAttendance(studentId, lessonId, otp);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Invalid Lesson", response.getBody());
    }

    @Test
    void markAttendance_shouldReturnPermissionDeniedException() throws PermissionDeniedException{

        when(studentService.markAttendance(studentId, lessonId, otp)).thenThrow(new PermissionDeniedException("OTP is incorrect"));

        ResponseEntity<String> response = studentController.markAttendance(studentId, lessonId, otp);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("OTP is incorrect", response.getBody());
    }

    @Test
    void getEnrolledCourses_shouldReturnEnrolledCourses() {
        List<Course> enrolledCourses = new ArrayList<>();
        enrolledCourses.add(course);
        when(studentService.getEnrolledCourses(studentId)).thenReturn(enrolledCourses);

        ResponseEntity<List<Course>> response = studentController.getEnrolledCourses(studentId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(enrolledCourses, response.getBody());
    }

    @Test
    void getGrades_shouldReturnAssignmentGrades() {
        List<AssignmentGrade> grades = new ArrayList<>();
        AssignmentGrade grade = new AssignmentGrade();
        grades.add(grade);
        when(studentService.getAssignmentGrades(studentId)).thenReturn(grades);

        ResponseEntity<List<AssignmentGrade>> response = studentController.getGrades(studentId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(grades, response.getBody());
    }
}