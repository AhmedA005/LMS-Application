package com.example.LMS.UserManagement.Student;

import com.example.LMS.CourseManagement.Assignment.Assignment;
import com.example.LMS.CourseManagement.Course.Course;
import com.example.LMS.CourseManagement.Enrollment.EnrollmentRequest;
import com.example.LMS.CourseManagement.Grade.AssignmentGrade;
import com.example.LMS.CourseManagement.Quiz.Quiz;
import com.example.LMS.PermissionDeniedException;
import com.example.LMS.mediafiles.MediaFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;
    private final MediaFileService mediaFileService;


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

    @GetMapping("/{studentId}/get-quiz")
    public ResponseEntity<String> getQuiz(@RequestParam Long quizId, @PathVariable Long studentId) {
        try {
            String quiz = studentService.getQuiz(quizId, studentId);
            return ResponseEntity.ok(quiz.toString());
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
        catch (PermissionDeniedException e){
            return ResponseEntity.ok(e.getMessage());
        }

    }

    @PostMapping("/{studentId}/take-quiz")
    public ResponseEntity<String> takeQuiz(@PathVariable Long studentId, @RequestParam Long quizId, @RequestBody List<String> answers) {
        try {
            String message = studentService.takeQuiz(studentId, quizId, answers);
            return ResponseEntity.ok(message);
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
        catch (PermissionDeniedException e){
            return ResponseEntity.ok(e.getMessage());
        }
    }

    @GetMapping("/{studentId}/get-assignment")
    public ResponseEntity<String> getAssignment(@RequestParam Long assignmentId, @PathVariable Long studentId) {
        try {
            Assignment assignment = studentService.getAssignment(assignmentId, studentId);
            return ResponseEntity.ok(assignment.toString());
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
        catch (PermissionDeniedException e){
            return ResponseEntity.ok(e.getMessage());
        }

    }

    @PostMapping("/{studentId}/submit-assignment")
    public ResponseEntity<String> submitAssignment(@PathVariable Long studentId, @RequestParam Long assignmentId, @RequestBody String answer) {
        try {
            String message = studentService.submitAssignment(studentId, assignmentId, answer);
            return ResponseEntity.ok(message);
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
        catch (PermissionDeniedException e){
            return ResponseEntity.ok(e.getMessage());
        }
    }

    @PostMapping("/{studentId}/submit-assignment")
    public ResponseEntity<String> submitAssignment(
            @PathVariable Long studentId,
            @RequestParam("assignmentId") Long assignmentId,
            @RequestParam("file") MultipartFile file) {
        try {
            mediaFileService.uploadMedia(file, assignmentId);
            return ResponseEntity.ok("Assignment submitted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }



}
