package com.example.LMS.notificationsystem;
import com.example.LMS.UserManagement.Instructor.Instructor;
import com.example.LMS.UserManagement.Student.Student;
import com.example.LMS.UserManagement.Student.StudentService;
import com.example.LMS.UserManagement.Instructor.InstructorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;
    private final StudentService studentService;
    private final InstructorService instructorService;
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Notification>> getNotificationsForStudent(@PathVariable Long studentId) {
        // Fetch student entity from student service/repository
        Student student = studentService.findById(studentId);
        return ResponseEntity.ok(notificationService.getNotificationsForStudent(student));
    }

    @GetMapping("/instructor/{instructorId}")
    public ResponseEntity<List<Notification>> getNotificationsForInstructor(@PathVariable Long instructorId) {
        // Fetch instructor entity from instructor service/repository
        Instructor instructor = instructorService.findById(instructorId);
        return ResponseEntity.ok(notificationService.getNotificationsForInstructor(instructor));
    }

    @PostMapping("/{notificationId}/mark-as-read")
    public ResponseEntity<String> markAsRead(@PathVariable Long notificationId) {
        notificationService.markAsRead(notificationId);
        return ResponseEntity.ok("Notification marked as read");
    }
}