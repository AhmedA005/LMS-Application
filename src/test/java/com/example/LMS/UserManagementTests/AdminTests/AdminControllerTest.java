package com.example.LMS.UserManagementTests.AdminTests;

import com.example.LMS.CourseManagement.Course.Course;
import com.example.LMS.CourseManagement.Course.CourseService;
import com.example.LMS.UserManagement.Admin.AdminController;
import com.example.LMS.UserManagement.Admin.AdminService;
import com.example.LMS.UserManagement.Instructor.Instructor;
import com.example.LMS.UserManagement.Instructor.InstructorService;
import com.example.LMS.UserManagement.Student.Student;
import com.example.LMS.UserManagement.Student.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdminControllerTest {

    @Mock
    private AdminService adminService;

    @Mock
    private StudentService studentService;

    @Mock
    private InstructorService instructorService;

    @Mock
    private CourseService courseService;

    @InjectMocks
    private AdminController adminController;

    private Student student;
    private Instructor instructor;
    private Course course;

    @BeforeEach
    void setUp() {
        student = new Student();
        student.setId(1L);
        student.setName("Test Student");

        instructor = new Instructor();
        instructor.setId(1L);
        instructor.setName("Test Instructor");

        course = new Course();
        course.setId(1L);
        course.setTitle("Test Course");
    }

    @Test
    void addStudent_shouldAddStudentAndReturnSuccessMessage() {
        ResponseEntity<String> response = adminController.addStudent(student);
        verify(adminService, times(1)).addStudent(student);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Student added successfully", response.getBody());
    }

    @Test
    void getStudents_shouldReturnAllStudents() {
        List<Student> students = new ArrayList<>();
        students.add(student);
        when(studentService.getAllStudents()).thenReturn(students);

        List<Student> result = adminController.getStudents();

        assertEquals(1, result.size());
        assertEquals(student, result.get(0));
    }

    @Test
    void addInstructor_shouldAddInstructorAndReturnSuccessMessage() {
        ResponseEntity<String> response = adminController.addInstructor(instructor);
        verify(adminService, times(1)).addInstructor(instructor);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Instructor added successfully", response.getBody());
    }

    @Test
    void getInstructors_shouldReturnAllInstructors() {
        List<Instructor> instructors = new ArrayList<>();
        instructors.add(instructor);
        when(instructorService.getAllInstructors()).thenReturn(instructors);

        List<Instructor> result = adminController.getInstructors();

        assertEquals(1, result.size());
        assertEquals(instructor, result.get(0));
    }

    @Test
    void getCourses_shouldReturnAllCourses() {
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        when(courseService.getAllCourses()).thenReturn(courses);

        List<Course> result = adminController.getCourses();

        assertEquals(1, result.size());
        assertEquals(course, result.get(0));
    }

    @Test
    void addCourse_shouldAddCourseAndReturnSuccessMessage() {
        ResponseEntity<String> response = adminController.addCourse(course);
        verify(adminService, times(1)).addCourse(course);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Course " + course.getTitle() + " added successfully", response.getBody());
    }

    @Test
    void deleteCourse_shouldDeleteCourseAndReturnSuccessMessage() {
        ResponseEntity<String> response = adminController.deleteCourse(course);
        verify(adminService, times(1)).removeCourse(course);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Course " + course.getTitle() + " deleted successfully", response.getBody());
    }

    @Test
    void getEnrolledStudents_shouldReturnEnrolledStudents() {
        Long courseId = 1L;
        List<Student> enrolledStudents = new ArrayList<>();
        enrolledStudents.add(student);

        when(adminService.getEnrolledStudents(courseId)).thenReturn(enrolledStudents);

        ResponseEntity<List<Student>> response = adminController.getEnrolledStudents(courseId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(enrolledStudents, response.getBody());
    }
}