package com.example.LMS.CourseManagement.Course;

import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.Map;

@Repository
public class CourseRepository {
    private final Map<Integer, Course> courseStorage = new HashMap<>();

    public void save(Course course) {
        courseStorage.put(course.getCourseId(), course);
    }

    public Course findById(int id) {
        return courseStorage.get(id);
    }

    public Map<Integer, Course> findAll() {
        return courseStorage;
    }
}
