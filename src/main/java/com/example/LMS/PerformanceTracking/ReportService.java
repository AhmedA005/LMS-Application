package com.example.LMS.PerformanceTracking;

import com.example.LMS.CourseManagement.Assignment.Assignment;
import com.example.LMS.CourseManagement.Assignment.AssignmentAttempt;
import com.example.LMS.CourseManagement.Assignment.AssignmentAttemptRepository;
import com.example.LMS.CourseManagement.Assignment.AssignmentRepository;
import com.example.LMS.CourseManagement.Course.Course;
import com.example.LMS.CourseManagement.Course.CourseRepository;
import com.example.LMS.CourseManagement.Enrollment.Enrollment;
import com.example.LMS.CourseManagement.Enrollment.EnrollmentRepository;
import com.example.LMS.CourseManagement.Quiz.Quiz;
import com.example.LMS.CourseManagement.Quiz.QuizAttempt;
import com.example.LMS.CourseManagement.Quiz.QuizAttemptRepository;
import com.example.LMS.CourseManagement.Quiz.QuizRepository;
import com.example.LMS.UserManagement.Student.Student;
import com.example.LMS.UserManagement.Student.StudentRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;


@Service
public class ReportService {

    @Autowired
    private AssignmentAttemptRepository assignmentAttemptRepository;

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private QuizAttemptRepository quizAttemptRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    public byte[] generateExcelReport(Long courseId) throws IOException {
        // Fetch the course and enrolled students
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new IllegalArgumentException("Invalid course ID"));
        List<Student> students = enrollmentRepository.findByCourse(course).stream()
                .map(Enrollment::getStudent)
                .collect(Collectors.toList());

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Student Performance");

        // Create header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Student ID");
        headerRow.createCell(1).setCellValue("Name");

        // Fetch assignments and quizzes for the course
        List<Assignment> assignments = assignmentRepository.findAllByCourseId(courseId);
        List<Quiz> quizzes = quizRepository.findAllByCourseId(courseId);

        // Create headers for assignments and quizzes
        int colNum = 2;
        for (Assignment assignment : assignments) {
            headerRow.createCell(colNum++).setCellValue("Assignment: " + assignment.getTitle());
        }
        for (Quiz quiz : quizzes) {
            headerRow.createCell(colNum++).setCellValue("Quiz: " + quiz.getTitle());
        }
        headerRow.createCell(colNum).setCellValue("Total Grades");

        // Populate student performance data
        int rowNum = 1;
        for (Student student : students) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(student.getId());
            row.createCell(1).setCellValue(student.getName());

            int totalGrades = 0;
            int currentCol = 2;

            for (Assignment assignment : assignments) {
                Optional<AssignmentAttempt> attempt = assignmentAttemptRepository
                        .findByStudentIdAndAssignmentId(student.getId(), assignment.getId());
                int score = attempt.map(AssignmentAttempt::getScore).orElse(0);
                row.createCell(currentCol++).setCellValue(score);
                totalGrades += score;
            }

            for (Quiz quiz : quizzes) {
                Optional<QuizAttempt> attempt = quizAttemptRepository
                        .findByStudentIdAndQuizId(student.getId(), quiz.getId());
                int score = attempt.map(QuizAttempt::getScore).orElse(0);
                row.createCell(currentCol++).setCellValue(score);
                totalGrades += score;
            }

            row.createCell(currentCol).setCellValue(totalGrades);
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();
        return outputStream.toByteArray();
    }

    public byte[] generatePerformanceChart(Long courseId) throws IOException {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new IllegalArgumentException("Invalid course ID"));
        List<Student> students = enrollmentRepository.findByCourse(course).stream()
                .map(Enrollment::getStudent)
                .collect(Collectors.toList());

        for (Student student : students) {
            int totalGrades = calculateTotalGrades(student.getId(), courseId);
            dataset.addValue(totalGrades, "Grades", student.getName());
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Student Performance",
                "Students",
                "Total Grades",
                dataset
        );

        ByteArrayOutputStream chartOutputStream = new ByteArrayOutputStream();
        ChartUtils.writeChartAsPNG(chartOutputStream, chart, 800, 600);
        return chartOutputStream.toByteArray();
    }

    private int calculateTotalGrades(Long studentId, Long courseId) {
        List<Assignment> assignments = assignmentRepository.findAllByCourseId(courseId);
        List<Quiz> quizzes = quizRepository.findAllByCourseId(courseId);

        int assignmentGrades = assignmentAttemptRepository.findAllByStudentIdAndAssignmentIdIn(
                        studentId, assignments.stream().map(Assignment::getId).collect(Collectors.toList()))
                .stream()
                .mapToInt(AssignmentAttempt::getScore)
                .sum();

        int quizGrades = quizAttemptRepository.findAllByStudentIdAndQuizIdIn(
                        studentId, quizzes.stream().map(Quiz::getId).collect(Collectors.toList()))
                .stream()
                .mapToInt(QuizAttempt::getScore)
                .sum();

        return assignmentGrades + quizGrades;
    }
}


/*@Service
public class ReportService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private AssignmentAttemptRepository assignmentLogRepository;

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private QuizAttemptRepository quizLogRepository;

    @Autowired
    private QuizRepository quizRepository;

    public byte[] generateExcelReport(Long courseId) throws IOException {
        List<Student> students = studentRepository.findAll(); // Fetch students for the course
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Student Performance");

        // Create header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Student ID");
        headerRow.createCell(1).setCellValue("Name");
        headerRow.createCell(2).setCellValue("Total Grades");
        headerRow.createCell(3).setCellValue("Attendance");

        int rowNum = 1;
        for (Student student : students) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(student.getId());
            row.createCell(1).setCellValue(student.getName());

            // Calculate total grades and attendance
            int totalGrades = calculateTotalGrades(student.getId(), courseId);
            int attendanceCount = calculateTotalGrades(student.getId(), courseId);

            row.createCell(2).setCellValue(totalGrades);
            row.createCell(3).setCellValue(attendanceCount);
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();
        return outputStream.toByteArray();
    }

    private int calculateTotalGrades(Long studentId, Long courseId) {
        // Ensure course assignments and quizzes are retrieved
        List<Assignment> assignments = assignmentRepository.findAllByCourseId(courseId);
        List<Quiz> quizzes = quizRepository.findAllByCourseId(courseId);

        // Handle cases where there are no assignments or quizzes
        if ((assignments == null || assignments.isEmpty()) && (quizzes == null || quizzes.isEmpty())) {
            return 0; // No assignments or quizzes found
        }

        // Retrieve assignment and quiz IDs for the course
        List<Long> assignmentIds = assignments != null ?
                assignments.stream().map(Assignment::getId).collect(Collectors.toList()) :
                Collections.emptyList();

        List<Long> quizIds = quizzes != null ?
                quizzes.stream().map(Quiz::getId).collect(Collectors.toList()) :
                Collections.emptyList();

        // Retrieve assignment and quiz logs for the student
        List<AssignmentAttempt> assignmentLogs = assignmentLogRepository.findAllByStudentIdAndAssignmentIdIn(studentId, assignmentIds);
        List<QuizAttempt> quizLogs = quizLogRepository.findAllByStudentIdAndQuizIdIn(studentId, quizIds);

        // Calculate total grades for assignments
        int assignmentTotalGrade = assignmentLogs != null ?
                assignmentLogs.stream().mapToInt(AssignmentAttempt::getScore).sum() : 0;

        // Calculate total grades for quizzes
        int quizTotalGrade = quizLogs != null ?
                quizLogs.stream().mapToInt(QuizAttempt::getScore).sum() : 0;

        // Return the combined total grade for assignments and quizzes
        return assignmentTotalGrade + quizTotalGrade;
    }


    public byte[] generatePerformanceChart(Long courseId) throws IOException {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        List<Student> students = studentRepository.findAll();

        for (Student student : students) {
            int totalGrades = calculateTotalGrades(student.getId(), courseId);
            dataset.addValue(totalGrades, "Grades", student.getName());
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Student Performance",
                "Students",
                "Total Grades",
                dataset
        );

        ByteArrayOutputStream chartOutputStream = new ByteArrayOutputStream();
        ChartUtils.writeChartAsPNG(chartOutputStream, chart, 800, 600);
        return chartOutputStream.toByteArray();
    }
}*/
