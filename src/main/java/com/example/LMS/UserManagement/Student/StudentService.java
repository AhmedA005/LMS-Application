package com.example.LMS.UserManagement.Student;

import com.example.LMS.CourseManagement.Assignment.Assignment;
import com.example.LMS.CourseManagement.Assignment.AssignmentAttempt;
import com.example.LMS.CourseManagement.Assignment.AssignmentAttemptRepository;
import com.example.LMS.CourseManagement.Assignment.AssignmentRepository;
import com.example.LMS.CourseManagement.Course.Course;
import com.example.LMS.CourseManagement.Enrollment.Enrollment;
import com.example.LMS.CourseManagement.Enrollment.EnrollmentRepository;
import com.example.LMS.CourseManagement.Grade.AssignmentGrade;
import com.example.LMS.CourseManagement.Grade.AssignmentGradeRepository;
import com.example.LMS.CourseManagement.Lesson.Lesson;
import com.example.LMS.CourseManagement.Lesson.LessonRepository;
import com.example.LMS.CourseManagement.Question.Question;
import com.example.LMS.CourseManagement.Quiz.Quiz;
import com.example.LMS.CourseManagement.Quiz.QuizAttempt;
import com.example.LMS.CourseManagement.Quiz.QuizAttemptRepository;
import com.example.LMS.CourseManagement.Quiz.QuizRepository;
import com.example.LMS.PerformanceTracking.Attendance;
import com.example.LMS.PerformanceTracking.AttendanceRepository;
import com.example.LMS.PermissionDeniedException;
import com.example.LMS.notificationsystemTests.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
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
    private final QuizAttemptRepository quizAttemptRepository;
    private final AssignmentRepository assignmentRepository;
    private final AssignmentAttemptRepository assignmentAttemptRepository;

    public Student findById(Long studentId) {
        return studentRepository.findById(studentId).orElseThrow(() -> new IllegalArgumentException("Student not found with ID: " + studentId));
    }

    public String enrollInCourse(Course course, Student student) {
        Enrollment enrollment = new Enrollment(null, course, student);
        enrollmentRepository.save(enrollment);

        // Send notification to the student
        notificationService.sendNotificationToStudent("You have been successfully enrolled in the course: " + course.getTitle(), student);

        return "Enrolled successfully!";
    }

    public String markAttendance(Long studentId, Long lessonId, String otp) {
        // Fetch the lesson
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() -> new IllegalArgumentException("Lesson not found"));

        // Validate the OTP
        if (!lesson.getOtp().equals(otp)) {
            throw new IllegalArgumentException("Invalid OTP");
        }

        // Fetch the course associated with the lesson
        Course course = lesson.getCourse();

        // Check if the student is enrolled in the course
        boolean isEnrolled = enrollmentRepository.findByCourse(course).stream().anyMatch(enrollment -> enrollment.getStudent().getId().equals(studentId));

        if (!isEnrolled) {
            throw new PermissionDeniedException("Student is not enrolled in the course associated with this lesson.");
        }

        // Mark attendance
        Attendance attendance = new Attendance(null, studentId, lessonId);
        attendanceRepository.save(attendance);
        String notification = "You attended" + lesson.getTitle() + "in" + course.getTitle() + "!";
        notificationService.sendNotificationToStudent(notification,studentRepository.findById(studentId).get());
        return "Attendance marked successfully!";
    }


    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public List<Course> getEnrolledCourses(Long studentId) {
        return enrollmentRepository.findAll().stream().filter(enrollment -> enrollment.getStudent().getId().equals(studentId)).map(Enrollment::getCourse).toList();
    }

    public List<AssignmentGrade> getAssignmentGrades(Long studentId) {
        return gradeRepository.findAll().stream().filter(grade -> grade.getStudentId().equals(studentId)).toList();
    }

    public Optional<Quiz> getQuiz(Long quizId, Long studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new IllegalArgumentException("Course not found"));
        Optional<Quiz> quiz = quizRepository.findById(quizId);

        if (!enrollmentRepository.findByCourse(quiz.get().getCourse()).stream().anyMatch(enrollment -> enrollment.getStudent().getId().equals(studentId)))
            throw new PermissionDeniedException("Student is not enrolled in the course associated with this quiz.");

        return quizRepository.findById(quizId);
    }

    public String takeQuiz(Long studentId, Long quizId, List<String> answers) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new IllegalArgumentException("Course not found"));
        Optional<Quiz> quiz = quizRepository.findById(quizId);

        if (!enrollmentRepository.findByCourse(quiz.get().getCourse()).stream().anyMatch(enrollment -> enrollment.getStudent().getId().equals(studentId)))
            throw new PermissionDeniedException("Student is not enrolled in the course associated with this quiz.");

        int i = 0, score = 0;
        for (Question question : quiz.get().getQuestions()) {
            if (question.isCorrect(answers.get(i++))) score++;
        }
        QuizAttempt attempt = new QuizAttempt();
        attempt.setScore(score);
        attempt.setQuiz(quiz.get());
        attempt.setStudentAnswers(answers);
        attempt.setAttemptDate(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
        attempt.setStudent(student);
        quizAttemptRepository.save(attempt);
        String feedback = "Your score in " + quiz.get().getTitle()+" is "+ score + " out of " + quiz.get().getNumberOfQuestions();
        notificationService.sendNotificationToStudent(feedback, student);
        attempt.setFeedback(feedback);
        return feedback;
    }

    public Assignment getAssignment(Long assignmentId, Long studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new IllegalArgumentException("Course not found"));
        Assignment assignment = assignmentRepository.findById(assignmentId).get();

        if (!enrollmentRepository.findByCourse(assignment.getCourse()).stream().anyMatch(enrollment -> enrollment.getStudent().getId().equals(studentId)))
            throw new PermissionDeniedException("Student is not enrolled in the course associated with this assignment.");

        return assignmentRepository.findById(assignmentId).get();
    }

    public String submitAssignment(Long studentId, Long assignmentId, String answer) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new IllegalArgumentException("Course not found"));
        Assignment assignment = assignmentRepository.findById(assignmentId).get();

        if (!enrollmentRepository.findByCourse(assignment.getCourse()).stream().anyMatch(enrollment -> enrollment.getStudent().getId().equals(studentId)))
            throw new PermissionDeniedException("Student is not enrolled in the course associated with this quiz.");

        AssignmentAttempt attempt = new AssignmentAttempt();
        attempt.setStudent(student);
        attempt.setAssignment(assignment);
        attempt.setAttemptDate(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
        attempt.setAnswer(answer);
        attempt.setFeedback("Pending");
        attempt.setGraded(false);
        assignmentAttemptRepository.save(attempt);
        notificationService.sendNotificationToInstructor("A student has submitted an assignment solution for " + assignment.getTitle(),assignment.getCourse().getInstructor());
        return "Submitted Successfully";
    }


}

