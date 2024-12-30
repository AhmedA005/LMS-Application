package com.example.LMS.UserManagement.Instructor;

import com.example.LMS.CourseManagement.Assignment.Assignment;
import com.example.LMS.CourseManagement.Course.Course;
import com.example.LMS.CourseManagement.Course.CourseService;
import com.example.LMS.CourseManagement.Grade.AssignmentGrade;
import com.example.LMS.CourseManagement.Lesson.Lesson;
import com.example.LMS.CourseManagement.Question.Question;
import com.example.LMS.CourseManagement.Quiz.Quiz;
import com.example.LMS.PerformanceTracking.ReportService;
import com.example.LMS.PermissionDeniedException;
import com.example.LMS.UserManagement.Student.Student;
import com.example.LMS.mediafiles.MediaFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/instructor")
@RequiredArgsConstructor
public class InstructorController {
    private final InstructorService instructorService;
    private final CourseService courseService;
    private final MediaFileService mediaFileService;
    private final ReportService reportService;

    // Add Course
    @PostMapping("/add-course")
    public ResponseEntity<String> createCourse(@RequestBody Course course) {
        instructorService.createCourse(course);
        return ResponseEntity.ok("Course " + course.getTitle() + " Added Successfully");
    }


    // Add Lesson
    @PostMapping("/{instructorId}/add-lesson")
    public ResponseEntity<String> addLesson(@PathVariable Long instructorId, @RequestBody Lesson lesson) {
        try {
            instructorService.addLesson(instructorId, lesson);
            return ResponseEntity.ok("Lesson added successfully");
        } catch (PermissionDeniedException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("You do not have permission to add lessons to this course.");
        }
    }

    // Remove Lesson
    @DeleteMapping("/{instructorId}/remove-lesson")
    public ResponseEntity<String> removeLesson(@PathVariable Long instructorId, @RequestBody Lesson lesson) {
        try {
            instructorService.removeLesson(instructorId, lesson);
            return ResponseEntity.ok("Lesson deleted successfully");
        } catch (PermissionDeniedException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("You do not have permission to remove this lesson.");
        }
    }

    // Add Assignment
    @PostMapping("/{instructorId}/add-assignment")
    public ResponseEntity<String> addAssignment(@PathVariable Long instructorId, @RequestBody Assignment assignment) {
        try {
            instructorService.addAssignment(instructorId, assignment);
            return ResponseEntity.ok("Assignment added successfully");
        } catch (PermissionDeniedException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("You do not have permission to add assignments to this course.");
        }
    }

    // Remove Assignment
    @DeleteMapping("/{instructorId}/remove-assignment")
    public ResponseEntity<String> removeAssignment(@PathVariable Long instructorId, @RequestBody Assignment assignment) {
        try {
            instructorService.removeAssignment(instructorId, assignment);
            return ResponseEntity.ok("Assignment deleted successfully");
        } catch (PermissionDeniedException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("You do not have permission to remove this assignment.");
        }
    }

    // Add Grade
    @PostMapping("/{instructorId}/add-assignment-grade")
    public ResponseEntity<String> addGrade(@PathVariable Long instructorId, @RequestBody AssignmentGrade grade) {
        try {
            instructorService.addAssignmentGrade(instructorId, grade);
            return ResponseEntity.ok("Grade added successfully");
        } catch (PermissionDeniedException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("You do not have permission to add grades to this course.");
        }
    }


    @GetMapping("/{courseId}/students")
    public ResponseEntity<List<Student>> getEnrolledStudents(@PathVariable Long courseId) {
        List<Student> students = instructorService.getEnrolledStudents(courseId);
        return ResponseEntity.ok(students);
    }

    @DeleteMapping("/{instructorId}/remove-student/{courseId}/{studentId}")
    public ResponseEntity<String> removeStudentFromCourse(
            @PathVariable Long instructorId,
            @PathVariable Long courseId,
            @PathVariable Long studentId) {
        try {
            instructorService.removeStudentFromCourse(instructorId, courseId, studentId);
            return ResponseEntity.ok("Student removed from course successfully");
        } catch (PermissionDeniedException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An unexpected error occurred");
        }
    }

    @PostMapping({"/{instructorId}/add-question"})
    public ResponseEntity<String> addQuestion(@PathVariable Long instructorId, @RequestBody Question question) {
        try {
            instructorService.addQuestion(instructorId, question);
            return ResponseEntity.ok("Question added successfully");
        } catch (PermissionDeniedException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("You do not have permission to add Question to this course.");
        }
    }

    @PostMapping({"/{instructorId}/add-quiz"})
    public ResponseEntity<String> addQuiz(@PathVariable Long instructorId, @RequestParam Long courseId, @RequestParam Quiz.QuizType quizType, @RequestParam int numberOfQuestions) {
        try {
            instructorService.createQuiz(courseId, instructorId, quizType, numberOfQuestions);
            return ResponseEntity.ok("Quiz added successfully");
        } catch (PermissionDeniedException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("You do not have permission to add Quiz to this course.");
        }
    }

    @GetMapping("/get-courses")
    public List<Course> getCourses() {
        return courseService.getAllCourses();
    }

    @PostMapping("/upload-course-content")
    public ResponseEntity<String> uploadCourseContent(
            @RequestParam("file") MultipartFile file,
            @RequestParam("courseId") Long courseId) {
        try {
            mediaFileService.uploadMedia(file, courseId);
            return ResponseEntity.ok("Course content uploaded successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/upload-assignment")
    public ResponseEntity<String> uploadAssignment(
            @RequestParam("file") MultipartFile file,
            @RequestParam("assignmentId") Long assignmentId) {
        try {
            mediaFileService.uploadMedia(file, assignmentId);
            return ResponseEntity.ok("Assignment uploaded successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
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

//    @GetMapping("/quizzes")
//    public ResponseEntity<List<Quiz>> getAllQuiz() {
//        return ResponseEntity.ok(instructorService.getAllQuizzes());
//    }
}
