package com.example.LMS.UserManagement.Student;

import com.example.LMS.CourseManagement.Course.Course;
import com.example.LMS.CourseManagement.Enrollment.EnrollmentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @PostMapping("/enroll")
    public ResponseEntity<String> enrollInCourse(@RequestBody EnrollmentRequest enrollmentRequest) {
        Course course = enrollmentRequest.getCourse();
        Student student = enrollmentRequest.getStudent();
        String message = studentService.enrollInCourse(course, student);
        return ResponseEntity.ok(message);
    }


    @PostMapping("/mark-attendance/{lessonId}")
    public ResponseEntity<String> markAttendance(@PathVariable Long lessonId, @RequestParam String otp) {
        String message = studentService.markAttendance(lessonId, otp);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/{studentId}/courses")
    public ResponseEntity<List<Course>> getEnrolledCourses(@PathVariable Long studentId) {
        List<Course> courses = studentService.getEnrolledCourses(studentId);
        return ResponseEntity.ok(courses);
    }
}

