package com.example.LMS.UserManagement.Instructor;

import com.example.LMS.CourseManagement.Assignment.Assignment;
import com.example.LMS.CourseManagement.Course.Course;
import com.example.LMS.CourseManagement.Grade.Grade;
import com.example.LMS.CourseManagement.Lesson.Lesson;
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
        Long instructorId = getAuthenticatedInstructorId();
        instructorService.addLesson(instructorId, lesson);
        return ResponseEntity.ok("Lesson added successfully");
    }


    @DeleteMapping("/remove-lesson")
    public ResponseEntity<String> removeLesson(@RequestBody Lesson lesson) {
        instructorService.removeLesson(lesson);
        return ResponseEntity.ok("Lesson deleted successfully");
    }

    @PostMapping("/add-assignment")
    public ResponseEntity<String> addAssignment(@RequestBody Assignment assignment) {
        Long instructorId = getAuthenticatedInstructorId(); // Replace with actual authenticated instructor ID
        instructorService.addAssignment(instructorId, assignment);
        return ResponseEntity.ok("Assignment added successfully");
    }


    // Mock method to simulate fetching authenticated instructor ID
    private Long getAuthenticatedInstructorId() {
        // Replace this with actual logic, such as fetching from a security context
        return 1L; // Example ID
    }


    @PostMapping("/remove-assignment")
    public ResponseEntity<String> removeAssignment(@RequestBody Assignment assignment) {
        instructorService.removeAssignment(assignment);
        return ResponseEntity.ok("Assignment deleted successfully");
    }

    @PostMapping("/add-grade")
    public ResponseEntity<String> addGrade(@RequestBody Grade grade) {
        Long instructorId = getAuthenticatedInstructorId();
        instructorService.addGrade(instructorId, grade);
        return ResponseEntity.ok("Grade added successfully");
    }


    @PostMapping("/remove-grade")
    public ResponseEntity<String> removeGrade(@RequestBody Grade grade) {
        instructorService.removeGrade(grade);
        return ResponseEntity.ok("Grade deleted successfully");
    }

}

