package com.example.LMS.UserManagement.Instructor;

import com.example.LMS.CourseManagement.Assignment.Assignment;
import com.example.LMS.CourseManagement.Course.Course;
import com.example.LMS.CourseManagement.Grade.Grade;
import com.example.LMS.CourseManagement.Lesson.Lesson;
import com.example.LMS.PermissionDeniedException;
import com.example.LMS.UserManagement.Student.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/instructor")
@RequiredArgsConstructor
public class InstructorController {
    private final InstructorService instructorService;

    // Add Course
    @PostMapping("/add-course")
    public ResponseEntity<String> createCourse(@RequestBody Course course) {
        instructorService.createCourse(course);
        return ResponseEntity.ok("Course " + course.getTitle() + " Added Successfully");
    }


    // Add Lesson
    @PostMapping("/add-lesson")
    public ResponseEntity<String> addLesson(@RequestBody Lesson lesson) {
        try {
            Long instructorId = getAuthenticatedInstructorId();
            instructorService.addLesson(instructorId, lesson);
            return ResponseEntity.ok("Lesson added successfully");
        } catch (PermissionDeniedException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("You do not have permission to add lessons to this course.");
        }
    }

    // Remove Lesson
    @DeleteMapping("/remove-lesson")
    public ResponseEntity<String> removeLesson(@RequestBody Lesson lesson) {
        try {
            Long instructorId = getAuthenticatedInstructorId();
            instructorService.removeLesson(instructorId, lesson);
            return ResponseEntity.ok("Lesson deleted successfully");
        } catch (PermissionDeniedException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("You do not have permission to remove this lesson.");
        }
    }

    // Add Assignment
    @PostMapping("/add-assignment")
    public ResponseEntity<String> addAssignment(@RequestBody Assignment assignment) {
        try {
            Long instructorId = getAuthenticatedInstructorId();
            instructorService.addAssignment(instructorId, assignment);
            return ResponseEntity.ok("Assignment added successfully");
        } catch (PermissionDeniedException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("You do not have permission to add assignments to this course.");
        }
    }

    // Remove Assignment
    @DeleteMapping("/remove-assignment")
    public ResponseEntity<String> removeAssignment(@RequestBody Assignment assignment) {
        try {
            Long instructorId = getAuthenticatedInstructorId();
            instructorService.removeAssignment(instructorId, assignment);
            return ResponseEntity.ok("Assignment deleted successfully");
        } catch (PermissionDeniedException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("You do not have permission to remove this assignment.");
        }
    }

    // Add Grade
    @PostMapping("/add-grade")
    public ResponseEntity<String> addGrade(@RequestBody Grade grade) {
        try {
            Long instructorId = getAuthenticatedInstructorId();
            instructorService.addGrade(instructorId, grade);
            return ResponseEntity.ok("Grade added successfully");
        } catch (PermissionDeniedException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("You do not have permission to add grades to this course.");
        }
    }

    // Remove Grade
    @DeleteMapping("/remove-grade")
    public ResponseEntity<String> removeGrade(@RequestBody Grade grade) {
        try {
            Long instructorId = getAuthenticatedInstructorId();
            instructorService.removeGrade(instructorId, grade);
            return ResponseEntity.ok("Grade deleted successfully");
        } catch (PermissionDeniedException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("You do not have permission to remove this grade.");
        }
    }

    // Mock method to simulate fetching authenticated instructor ID
    private Long getAuthenticatedInstructorId() {
        return 1L; // Example ID
    }

    @GetMapping("/{courseId}/students")
    public ResponseEntity<List<Student>> getEnrolledStudents(@PathVariable Long courseId) {
        List<Student> students = instructorService.getEnrolledStudents(courseId);
        return ResponseEntity.ok(students);
    }
}
