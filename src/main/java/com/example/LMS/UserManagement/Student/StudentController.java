package com.example.LMS.UserManagement.Student;

import com.example.LMS.CourseManagement.Course.Course;
import com.example.LMS.CourseManagement.Enrollment.EnrollmentRequest;
import com.example.LMS.CourseManagement.Grade.AssignmentGrade;
import com.example.LMS.PermissionDeniedException;
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


    @PostMapping("/{studentId}/mark-attendance/{lessonId}")
    public ResponseEntity<String> markAttendance(@PathVariable Long studentId,@PathVariable Long lessonId, @RequestParam String otp) {
        try {
            String message = studentService.markAttendance(studentId, lessonId, otp);
            return ResponseEntity.ok(message);
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
        catch (PermissionDeniedException e){
            return ResponseEntity.ok(e.getMessage());
        }
    }

    @GetMapping("/{studentId}/courses")
    public ResponseEntity<List<Course>> getEnrolledCourses(@PathVariable Long studentId) {
        List<Course> courses = studentService.getEnrolledCourses(studentId);
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/{studentId}/assignment-grades")
    public ResponseEntity<List<AssignmentGrade>> getGrades(@PathVariable Long studentId) {
        List<AssignmentGrade> grades = studentService.getAssignmentGrades(studentId);
        return ResponseEntity.ok(grades);
    }



}

