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
import com.example.LMS.CourseManagement.Question.*;
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
import java.util.*;

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
    private final MCQQuestionRepository mcqQuestionRepository;
    private final TrueFalseQuestionRepository trueFalseQuestionRepository;
    private final ShortAnswerRepository shortAnswerRepository;

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

    public String getQuiz(Long quizId, Long studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new IllegalArgumentException("Course not found"));
        Quiz quiz = quizRepository.findById(quizId).get();

        if (!enrollmentRepository.findByCourse(quiz.getCourse()).stream().anyMatch(enrollment -> enrollment.getStudent().getId().equals(studentId)))
            throw new PermissionDeniedException("Student is not enrolled in the course associated with this quiz.");

        Course course = quiz.getCourse();

        List<Question> questions = null;

        // Based on the quizType, fetch the questions from the appropriate repository
        if (quiz.getQuizType() == Quiz.QuizType.MCQ) {
            questions = mcqQuestionRepository.findByCourseId(course.getId());
        } else if (quiz.getQuizType() == Quiz.QuizType.ShortAnswer) {
            questions = shortAnswerRepository.findByCourseId(course.getId());
        } else if (quiz.getQuizType() == Quiz.QuizType.TrueFalse) {
            questions = trueFalseQuestionRepository.findByCourseId(course.getId());
        } else {
            throw new IllegalArgumentException("Invalid quiz type.");
        }

        Set<Question> questionSet = new HashSet<>();

        // Randomly select questions
        while (questionSet.size() < quiz.getNumberOfQuestions() && !questions.isEmpty()) {
            int questionNumber = (int) (Math.random() * questions.size());
            questionSet.add(questions.get(questionNumber));
        }

        // Convert the Set to a List (if needed)
        List<Question> finalQuestions = new ArrayList<>(questionSet);

        String output = "Number of questions: " + quiz.getNumberOfQuestions() + "\n" + quiz.getQuizType().toString();
        for (Question question : finalQuestions) {
            output += "\n" + question.getQuestionText();
            if(question instanceof MCQQuestion)
                output += "\n" + ((MCQQuestion) question).getOptions();
            output += "\n";
        }


        QuizAttempt quizAttempt = new QuizAttempt();
        quizAttempt.setQuiz(quiz);
        quizAttempt.setStudent(student);
        quizAttempt.setQuestions(finalQuestions);
        quizAttemptRepository.save(quizAttempt);
        return output;
    }

    public String takeQuiz(Long studentId, Long quizId, List<String> answers) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new IllegalArgumentException("Course not found"));
        Quiz quiz = quizRepository.findById(quizId).get();
        QuizAttempt quizAttempt = quizAttemptRepository.findByQuizAndStudent(quiz,student);

        if(quizAttempt.getFeedback() != null) {
            return "You already submitted an answer for this exam and your score is: " + quizAttempt.getScore();
        }


        int i = 0, score = 0;
        for (Question question : quizAttempt.getQuestions()) {
            if (question.isCorrect(answers.get(i++))) score++;
        }
        quizAttempt.setScore(score);
        quizAttempt.setStudentAnswers(answers);
        quizAttempt.setAttemptDate(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
        quizAttemptRepository.save(quizAttempt);
        String feedback = "Your score in " + quiz.getTitle()+" is "+ score + " out of " + quiz.getNumberOfQuestions();
        notificationService.sendNotificationToStudent(feedback, student);
        quizAttempt.setFeedback(feedback);
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

