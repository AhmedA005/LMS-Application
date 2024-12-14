package com.example.LMS.UserManagement.Student;

import com.example.LMS.CourseManagement.Course.Course;
import com.example.LMS.CourseManagement.Enrollment.Enrollment;
import com.example.LMS.CourseManagement.Enrollment.EnrollmentRepository;
import com.example.LMS.CourseManagement.Grade.AssignmentGrade;
import com.example.LMS.CourseManagement.Grade.AssignmentGradeRepository;
import com.example.LMS.CourseManagement.Lesson.Lesson;
import com.example.LMS.CourseManagement.Lesson.LessonRepository;
import com.example.LMS.PerformanceTracking.Attendance;
import com.example.LMS.PerformanceTracking.AttendanceRepository;
import com.example.LMS.PermissionDeniedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final LessonRepository lessonRepository;
    private final AssignmentGradeRepository gradeRepository;
    private final AttendanceRepository attendanceRepository;

    public String enrollInCourse(Course course, Student student) {
        Enrollment enrollment = new Enrollment(null, course, student);
        enrollmentRepository.save(enrollment);
        return "Enrolled successfully!";
    }

    public String markAttendance(Long studentId, Long lessonId, String otp) {
        // Fetch the lesson
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new IllegalArgumentException("Lesson not found"));

        // Validate the OTP
        if (!lesson.getOtp().equals(otp)) {
            throw new IllegalArgumentException("Invalid OTP");
        }

        // Fetch the course associated with the lesson
        Course course = lesson.getCourse();

        // Check if the student is enrolled in the course
        boolean isEnrolled = enrollmentRepository.findByCourse(course)
                .stream()
                .anyMatch(enrollment -> enrollment.getStudent().getId().equals(studentId));

        if (!isEnrolled) {
            throw new PermissionDeniedException("Student is not enrolled in the course associated with this lesson.");
        }

        // Mark attendance
        Attendance attendance = new Attendance(null, studentId, lessonId);
        attendanceRepository.save(attendance);
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

    public List<AssignmentGrade> getAssignmentGrades(Long studentId) {
        return gradeRepository.findAll()
                .stream()
                .filter(grade -> grade.getStudentId().equals(studentId))
                .toList();
    }


}

