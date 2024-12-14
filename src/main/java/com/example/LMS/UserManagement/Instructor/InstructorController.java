package com.example.LMS.UserManagement.Instructor;

import com.example.LMS.CourseManagement.Assignment.Assignment;
import com.example.LMS.CourseManagement.Course.Course;
import com.example.LMS.CourseManagement.Grade.AssignmentGrade;
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
    @PostMapping("/{instructorId}/add-lesson")
    public ResponseEntity<String> addLesson(@PathVariable Long instructorId,@RequestBody Lesson lesson) {
        try {
            instructorService.addLesson(instructorId, lesson);
            return ResponseEntity.ok("Lesson added successfully");
        } catch (PermissionDeniedException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("You do not have permission to add lessons to this course.");
        }
    }

    // Remove Lesson
    @DeleteMapping("/{instructorId}/remove-lesson")
    public ResponseEntity<String> removeLesson(@PathVariable Long instructorId,@RequestBody Lesson lesson) {
        try {
            instructorService.removeLesson(instructorId, lesson);
            return ResponseEntity.ok("Lesson deleted successfully");
        } catch (PermissionDeniedException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("You do not have permission to remove this lesson.");
        }
    }

    // Add Assignment
    @PostMapping("/{instructorId}/add-assignment")
    public ResponseEntity<String> addAssignment(@PathVariable Long instructorId,@RequestBody Assignment assignment) {
        try {
            instructorService.addAssignment(instructorId, assignment);
            return ResponseEntity.ok("Assignment added successfully");
        } catch (PermissionDeniedException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("You do not have permission to add assignments to this course.");
        }
    }

    // Remove Assignment
    @DeleteMapping("/{instructorId}/remove-assignment")
    public ResponseEntity<String> removeAssignment(@PathVariable Long instructorId,@RequestBody Assignment assignment) {
        try {
            instructorService.removeAssignment(instructorId, assignment);
            return ResponseEntity.ok("Assignment deleted successfully");
        } catch (PermissionDeniedException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("You do not have permission to remove this assignment.");
        }
    }

    // Add Grade
    @PostMapping("/{instructorId}/add-assignment-grade")
    public ResponseEntity<String> addGrade(@PathVariable Long instructorId,@RequestBody AssignmentGrade grade) {
        try {
            instructorService.addAssignmentGrade(instructorId, grade);
            return ResponseEntity.ok("Grade added successfully");
        } catch (PermissionDeniedException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("You do not have permission to add grades to this course.");
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

    @DeleteMapping("/{instructorId}/remove-student/{courseId}/{studentId}")
    public ResponseEntity<String> removeStudentFromCourse(
            @PathVariable Long instructorId,
            @PathVariable Long courseId,
            @PathVariable Long studentId) {
        try {
            instructorService.removeStudentFromCourse(instructorId, courseId, studentId);
            return ResponseEntity.ok("Student removed from course successfully");
        } catch (PermissionDeniedException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An unexpected error occurred");
        }
    }

}
