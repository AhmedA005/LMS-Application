package com.example.LMS.UserManagement.Admin;

import com.example.LMS.CourseManagement.Course.Course;
import com.example.LMS.CourseManagement.Course.CourseRepository;
import com.example.LMS.CourseManagement.Enrollment.Enrollment;
import com.example.LMS.CourseManagement.Enrollment.EnrollmentRepository;
import com.example.LMS.UserManagement.Instructor.Instructor;
import com.example.LMS.UserManagement.Instructor.InstructorRepository;
import com.example.LMS.UserManagement.Instructor.InstructorService;
import com.example.LMS.UserManagement.Student.Student;
import com.example.LMS.UserManagement.Student.StudentRepository;
import com.example.LMS.UserManagement.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final StudentRepository studentRepository;
    private final InstructorRepository instructorRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;

    public void addStudent(Student student) {
        if (student == null || student.getEmail() == null) {
            throw new IllegalArgumentException("Invalid student details provided");
        }
        if (studentRepository.findStudentByEmail(student.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already in use");
        }
        studentRepository.save(student);
    }

    public void addInstructor(Instructor instructor) {
        if (instructor == null || instructor.getEmail() == null) {
            throw new IllegalArgumentException("Invalid student details provided");
        }
        if (instructorRepository.findByEmail(instructor.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already in use");
        }
        instructorRepository.save(instructor);
    }

    public void addCourse(Course course) {
        courseRepository.save(course);
    }

    public void removeCourse(Course course) {
        courseRepository.delete(course);
    }

    public List<Student> getEnrolledStudents(Long courseId) {
        Course course = new Course(courseId);
        return enrollmentRepository.findByCourse(course)
                .stream()
                .map(Enrollment::getStudent)
                .collect(Collectors.toList());
    }

}

