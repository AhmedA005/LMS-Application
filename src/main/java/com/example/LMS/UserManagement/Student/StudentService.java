package com.example.LMS.UserManagement.Student;

import com.example.LMS.CourseManagement.Course.Course;
import com.example.LMS.CourseManagement.Enrollment.Enrollment;
import com.example.LMS.CourseManagement.Enrollment.EnrollmentRepository;
import com.example.LMS.CourseManagement.Lesson.Lesson;
import com.example.LMS.CourseManagement.Lesson.LessonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final LessonRepository lessonRepository;

    public String enrollInCourse(Course course, Student student) {
        Enrollment enrollment = new Enrollment(null, course, student);
        enrollmentRepository.save(enrollment);
        return "Enrolled successfully!";
    }

    public String markAttendance(Long lessonId, String otp) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new IllegalArgumentException("Lesson not found"));

        if (!lesson.getOtp().equals(otp)) {
            throw new IllegalArgumentException("Invalid OTP");
        }
        return "Attendance marked successfully!";
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public List<Course> getEnrolledCourses(Long studentId) {
        return enrollmentRepository.findAll()
                .stream()
                .filter(enrollment -> enrollment.getStudent().getId().equals(studentId))
                .map(Enrollment::getCourse)
                .toList();
    }
}

