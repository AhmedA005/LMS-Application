package com.example.LMS.UserManagement.Instructor;

import com.example.LMS.CourseManagement.Assignment.Assignment;
import com.example.LMS.CourseManagement.Course.Course;
import com.example.LMS.CourseManagement.Grade.Grade;
import com.example.LMS.CourseManagement.Lesson.Lesson;
import com.example.LMS.PermissionDeniedException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/instructor")
@RequiredArgsConstructor
public class InstructorController {
    private final InstructorService instructorService;

    @PostMapping("/add-course")
    public ResponseEntity<String> createCourse(@RequestBody Course course) {
        instructorService.createCourse(course);
        return ResponseEntity.ok("Course " + course.getTitle() + "Added Successfully");
    }

    @PutMapping("/update-course/{id}")
    public ResponseEntity<String> updateCourse(@PathVariable Long id, @RequestBody Course course) {
        instructorService.updateCourse(id, course);
        return ResponseEntity.ok("Course updated successfully");
    }

    @PostMapping("/add-lesson")
    public ResponseEntity<String> addLesson(@RequestBody Lesson lesson) {
        try{
        Long instructorId = getAuthenticatedInstructorId();
        instructorService.addLesson(instructorId, lesson);
        return ResponseEntity.ok("Lesson added successfully");
    } catch (PermissionDeniedException e) {
        // Return a 403 Forbidden response with the custom error message
        return ResponseEntity.status(403).body(e.getMessage());
    } catch (Exception e) {
        // Handle any other exceptions with a generic error message
        return ResponseEntity.status(500).body("You do not have permission to add lessons to this course.");
    }
    }


    @DeleteMapping("/remove-lesson")
    public ResponseEntity<String> removeLesson(@RequestBody Lesson lesson) {
        instructorService.removeLesson(lesson);
        return ResponseEntity.ok("Lesson deleted successfully");
    }


        @PostMapping("/add-assignment")
        public ResponseEntity<String> addAssignment(@RequestBody Assignment assignment) {
            try {
                Long instructorId = getAuthenticatedInstructorId(); // Get the authenticated instructor ID
                instructorService.addAssignment(instructorId, assignment);
                return ResponseEntity.ok("Assignment added successfully");
            } catch (PermissionDeniedException e) {
                // Return a 403 Forbidden response with the custom error message
                return ResponseEntity.status(403).body(e.getMessage());
            } catch (Exception e) {
                // Handle any other exceptions with a generic error message
                return ResponseEntity.status(500).body("You do not have permission to add Assignments to this course.");
            }
        }

        // Mock method to simulate fetching authenticated instructor ID
        private Long getAuthenticatedInstructorId() {
            return 1L; // Example ID
        }



    @PostMapping("/remove-assignment")
    public ResponseEntity<String> removeAssignment(@RequestBody Assignment assignment) {
        instructorService.removeAssignment(assignment);
        return ResponseEntity.ok("Assignment deleted successfully");
    }

    @PostMapping("/add-grade")
    public ResponseEntity<String> addGrade(@RequestBody Grade grade) {
        try {
        Long instructorId = getAuthenticatedInstructorId();
        instructorService.addGrade(instructorId, grade);
        return ResponseEntity.ok("Grade added successfully");
    } catch (PermissionDeniedException e) {
        // Return a 403 Forbidden response with the custom error message
        return ResponseEntity.status(403).body(e.getMessage());
    } catch (Exception e) {
        // Handle any other exceptions with a generic error message
        return ResponseEntity.status(500).body("You do not have permission to add grades to this course.");
    }
    }


    @PostMapping("/remove-grade")
    public ResponseEntity<String> removeGrade(@RequestBody Grade grade) {
        instructorService.removeGrade(grade);
        return ResponseEntity.ok("Grade deleted successfully");
    }

}

