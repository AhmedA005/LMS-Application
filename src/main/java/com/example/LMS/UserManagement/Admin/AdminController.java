package com.example.LMS.UserManagement.Admin;

import com.example.LMS.Authentication.UserInfo;
import com.example.LMS.Authentication.UserInfoService;
import com.example.LMS.CourseManagement.Course.Course;
import com.example.LMS.CourseManagement.Course.CourseService;
import com.example.LMS.PerformanceTracking.ReportService;
import com.example.LMS.UserManagement.Instructor.Instructor;
import com.example.LMS.UserManagement.Instructor.InstructorService;
import com.example.LMS.UserManagement.Student.Student;
import com.example.LMS.UserManagement.Student.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;
    private final StudentService studentService;
    private final InstructorService instructorService;
    private final CourseService courseService;
    private final UserInfoService userInfoService;
    private final ReportService reportService;

    @PostMapping("/add-student")
    public ResponseEntity<String> addStudent(@RequestBody Student student) {
        System.out.println("Received student: " + student);
        adminService.addStudent(student);
        userInfoService.addUser(student);
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
        userInfoService.addUser(instructor);
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

    @GetMapping("/{courseId}/students")
    public ResponseEntity<List<Student>> getEnrolledStudents(@PathVariable Long courseId) {
        List<Student> students = adminService.getEnrolledStudents(courseId);
        return ResponseEntity.ok(students);
    }

    @GetMapping("get-users")
    public List<UserInfo> getUsers() {
        return userInfoService.getAllUsers();
    }

    @GetMapping("/generate-report")
    public ResponseEntity<byte[]> generateReport(@RequestParam Long courseId) {
        try {
            byte[] report = reportService.generateExcelReport(courseId);
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=student_performance_report.xlsx")
                    .body(report);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/generate-performance-chart")
    public ResponseEntity<byte[]> generatePerformanceChart(@RequestParam Long courseId) {
        try {
            byte[] chart = reportService.generatePerformanceChart(courseId);
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=student_performance_chart.png")
                    .body(chart);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}

