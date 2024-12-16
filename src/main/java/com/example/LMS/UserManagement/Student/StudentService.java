package com.example.LMS.UserManagement.Student;

import com.example.LMS.CourseManagement.Course.Course;
import com.example.LMS.CourseManagement.Enrollment.Enrollment;
import com.example.LMS.CourseManagement.Enrollment.EnrollmentRepository;
import com.example.LMS.CourseManagement.Grade.AssignmentGrade;
import com.example.LMS.CourseManagement.Grade.AssignmentGradeRepository;
import com.example.LMS.CourseManagement.Lesson.Lesson;
import com.example.LMS.CourseManagement.Lesson.LessonRepository;
import com.example.LMS.CourseManagement.Question.Question;
import com.example.LMS.CourseManagement.Quiz.Quiz;
import com.example.LMS.CourseManagement.Quiz.QuizRepository;
import com.example.LMS.PerformanceTracking.Attendance;
import com.example.LMS.PerformanceTracking.AttendanceRepository;
import com.example.LMS.PermissionDeniedException;
import com.example.LMS.notificationsystemTests.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final LessonRepository lessonRepository;
    private final AssignmentGradeRepository gradeRepository;
    private final AttendanceRepository attendanceRepository;
    private final NotificationService notificationService;
    private final QuizRepository quizRepository;
    public Student findById(Long studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found with ID: " + studentId));
    }
    public String enrollInCourse(Course course, Student student) {
        Enrollment enrollment = new Enrollment(null, course, student);
        enrollmentRepository.save(enrollment);

        // Send notification to the student
        notificationService.sendNotificationToStudent(
                "You have been successfully enrolled in the course: " + course.getTitle(),
                student
        );

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

    public Optional<Quiz> getQuiz(Long quizId) {
        return quizRepository.findById(quizId);
    }

    public void takeQuiz(Long studentId, Long quizId, List<String>answers) {
        Optional<Quiz> quiz = quizRepository.findById(quizId);
        int i=0;
        for(Question question : quiz.get().getQuestions()){

        }
    }


}

