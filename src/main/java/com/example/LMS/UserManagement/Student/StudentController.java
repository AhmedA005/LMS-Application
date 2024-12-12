package com.example.LMS.UserManagement.Student;

import com.example.LMS.CourseManagement.Course.Course;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @PostMapping("/enroll")
    public ResponseEntity<String> enrollInCourse(@RequestBody Course course, @RequestBody Student student) {
        String message = studentService.enrollInCourse(course, student);
        return ResponseEntity.ok(message);
    }

    @PostMapping("/mark-attendance/{lessonId}")
    public ResponseEntity<String> markAttendance(@PathVariable Long lessonId, @RequestParam String otp) {
        String message = studentService.markAttendance(lessonId, otp);
        return ResponseEntity.ok(message);
    }
}

