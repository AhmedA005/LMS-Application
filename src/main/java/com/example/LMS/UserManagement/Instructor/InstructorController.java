package com.example.LMS.UserManagement.Instructor;

import com.example.LMS.CourseManagement.Course.Course;
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
    public ResponseEntity<Lesson> addLesson(@RequestBody Lesson lesson) {
        return ResponseEntity.ok(instructorService.addLesson(lesson));
    }


}

