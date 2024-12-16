package com.example.LMS.notificationsystemTests;
import com.example.LMS.UserManagement.Instructor.Instructor;
import com.example.LMS.UserManagement.Instructor.InstructorService;
import com.example.LMS.UserManagement.Student.Student;
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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NotificationControllerTest {

    @Mock
    private NotificationService notificationService;

    @Mock
    private StudentService studentService;

    @Mock
    private InstructorService instructorService;

    @InjectMocks
    private NotificationController notificationController;

    private Student testStudent;
    private Instructor testInstructor;
    private List<Notification> testNotifications;

    @BeforeEach
    void setUp() {
        testStudent = new Student();
        testStudent.setId(1L);
        testInstructor = new Instructor();
        testInstructor.setId(1L);
        testNotifications = new ArrayList<>();
        testNotifications.add(new Notification());
    }

    @Test
    void getNotificationsForStudent_ValidStudentId_ReturnsNotifications() {
        when(studentService.findById(1L)).thenReturn(testStudent);
        when(notificationService.getNotificationsForStudent(testStudent)).thenReturn(testNotifications);

        ResponseEntity<List<Notification>> response = notificationController.getNotificationsForStudent(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testNotifications, response.getBody());
        verify(studentService).findById(1L);
        verify(notificationService).getNotificationsForStudent(testStudent);
    }

    @Test
    void getNotificationsForStudent_InvalidStudentId_ReturnsNullNotifications() {
        when(studentService.findById(1L)).thenReturn(null);

        ResponseEntity<List<Notification>> response = notificationController.getNotificationsForStudent(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());

        verify(studentService).findById(1L);
    }

    @Test
    void getNotificationsForInstructor_ValidInstructorId_ReturnsNotifications() {
        when(instructorService.findById(1L)).thenReturn(testInstructor);
        when(notificationService.getNotificationsForInstructor(testInstructor)).thenReturn(testNotifications);

        ResponseEntity<List<Notification>> response = notificationController.getNotificationsForInstructor(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testNotifications, response.getBody());
        verify(instructorService).findById(1L);
        verify(notificationService).getNotificationsForInstructor(testInstructor);
    }

    @Test
    void getNotificationsForInstructor_InvalidInstructorId_ReturnsNullNotifications() {
        when(instructorService.findById(1L)).thenReturn(null);

        ResponseEntity<List<Notification>> response = notificationController.getNotificationsForInstructor(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
        verify(instructorService).findById(1L);
    }

    @Test
    void markAsRead_ValidNotificationId_ReturnsSuccessMessage() {
        ResponseEntity<String> response = notificationController.markAsRead(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Notification marked as read", response.getBody());
        verify(notificationService).markAsRead(1L);
    }

    
}
