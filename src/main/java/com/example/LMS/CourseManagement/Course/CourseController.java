package com.example.LMS.CourseManagement.Course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {
    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    public void createCourse(@RequestBody Course course) {
        courseService.addCourse(course);
    }

    @GetMapping("/{id}")
    public Course getCourse(@PathVariable int id) {
        return courseService.getCourse(id);
    }

    @GetMapping
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }
}
