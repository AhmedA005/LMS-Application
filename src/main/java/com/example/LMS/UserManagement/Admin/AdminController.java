package com.example.LMS.UserManagement.Admin;

import com.example.LMS.CourseManagement.Course.Course;
import com.example.LMS.CourseManagement.Course.CourseService;
import com.example.LMS.UserManagement.Instructor.Instructor;
import com.example.LMS.UserManagement.Instructor.InstructorService;
import com.example.LMS.UserManagement.Student.Student;
import com.example.LMS.UserManagement.Student.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;
    private final StudentService studentService;
    private final InstructorService instructorService;
    private final CourseService courseService;

    @PostMapping("/add-student")
    public ResponseEntity<String> addStudent(@RequestBody Student student) {
        System.out.println("Received student: " + student);
        adminService.addStudent(student);
        return ResponseEntity.ok("Student added successfully");
    }

    @GetMapping("/get-students")
    public List<Student> getStudents() {
        return studentService.getAllStudents();
    }

    @PostMapping("/add-instructor")
    public ResponseEntity<String> addInstructor(@RequestBody Instructor instructor) {
        System.out.println("Received Instructor: " + instructor);
        adminService.addInstructor(instructor);
        return ResponseEntity.ok("Instructor added successfully");
    }

    @GetMapping("/get-instructors")
    public List<Instructor> getInstructors() {
        return instructorService.getAllInstructors();
    }

    @GetMapping("/get-courses")
    public List<Course> getCourses() {
        return courseService.getAllCourses();
    }

    @PostMapping("/add-course")
    public ResponseEntity<String> addCourse(@RequestBody Course course) {
        adminService.addCourse(course);
        return ResponseEntity.ok("Course " + course.getTitle() + " added successfully");
    }

    @DeleteMapping("/delete-course")
    public ResponseEntity<String> deleteCourse(@RequestBody Course course) {
        adminService.removeCourse(course);
        return ResponseEntity.ok("Course " + course.getTitle() + " deleted successfully");
    }
}

