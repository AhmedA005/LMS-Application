package com.example.LMS.UserManagement.Instructor;

import com.example.LMS.CourseManagement.Assignment.Assignment;
import com.example.LMS.CourseManagement.Assignment.AssignmentRepository;
import com.example.LMS.CourseManagement.Course.Course;
import com.example.LMS.CourseManagement.Course.CourseRepository;
import com.example.LMS.CourseManagement.Enrollment.Enrollment;
import com.example.LMS.CourseManagement.Enrollment.EnrollmentRepository;
import com.example.LMS.CourseManagement.Grade.AssignmentGrade;
import com.example.LMS.CourseManagement.Grade.AssignmentGradeRepository;
import com.example.LMS.CourseManagement.Lesson.Lesson;
import com.example.LMS.CourseManagement.Lesson.LessonRepository;
import com.example.LMS.PermissionDeniedException;
import com.example.LMS.UserManagement.Student.Student;
import com.example.LMS.notificationsystem.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InstructorService {
    private final InstructorRepository instructorRepository;
    private final CourseRepository courseRepository;
    private final LessonRepository lessonRepository;
    private final AssignmentRepository assignmentRepository;
    private final AssignmentGradeRepository AssignmentGradeRepository;
    private final EnrollmentRepository enrollmentRepository;
   private final NotificationService notificationService;
    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    private String generateOTP(){
        String numbers = "0123456789";
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int index = (int) (Math.random() * numbers.length());
            otp.append(numbers.charAt(index));
        }
        return otp.toString();
    }
    public Instructor findById(Long instructorId) {
        // Use the repository to fetch the instructor, or throw an exception if not found
        return instructorRepository.findById(instructorId)
                .orElseThrow(() -> new IllegalArgumentException("Instructor not found with ID: " + instructorId));
    }

    // Add Lesson
    public void addLesson(Long instructorId, Lesson lesson) {
        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new IllegalArgumentException("Instructor not found"));

        Course course = courseRepository.findById(lesson.getCourse().getId())
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        if (!course.getInstructor().getId().equals(instructorId)) {
            throw new PermissionDeniedException("Instructor does not have permission to add lessons to this course.");
        }
        lesson.setOtp(generateOTP());
        lesson.setCourse(course);
        lessonRepository.save(lesson);
        List<Enrollment> enrollments = enrollmentRepository.findByCourse(course);
        for (Enrollment enrollment : enrollments) {
            notificationService.sendNotificationToStudent(
                    "A new lesson has been added to your course: " + course.getTitle(),
                    enrollment.getStudent()
            );
        }
    }

    // Remove Lesson
    public void removeLesson(Long instructorId, Lesson lesson) {
        Course course = courseRepository.findById(lesson.getCourse().getId())
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));


        if (!course.getInstructor().getId().equals(instructorId)) {
            throw new PermissionDeniedException("Instructor does not have permission to remove lessons from this course.");
        }

        lessonRepository.delete(lesson);
    }

    // Add Assignment
    public void addAssignment(Long instructorId, Assignment assignment) {
        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new IllegalArgumentException("Instructor not found"));

        Course course = courseRepository.findById(assignment.getCourse().getId())
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        if (!course.getInstructor().getId().equals(instructorId)) {
            throw new PermissionDeniedException("Instructor does not have permission to add assignments to this course.");
        }

        assignment.setCourse(course);
        assignmentRepository.save(assignment);
    }

    // Remove Assignment
    public void removeAssignment(Long instructorId, Assignment assignment) {
        Course course = courseRepository.findById(assignment.getCourse().getId())
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));


        if (!course.getInstructor().getId().equals(instructorId)) {
            throw new PermissionDeniedException("Instructor does not have permission to remove assignments from this course.");
        }

        assignmentRepository.delete(assignment);
    }

    // Add Grade
//    public void addGrade(Long instructorId, Grade grade) {
//        Instructor instructor = instructorRepository.findById(instructorId)
//                .orElseThrow(() -> new IllegalArgumentException("Instructor not found"));
//
//        Course course = courseRepository.findById(grade.getCourse().getId())
//                .orElseThrow(() -> new IllegalArgumentException("Course not found"));
//
//        if (!course.getInstructor().getId().equals(instructorId)) {
//            throw new PermissionDeniedException("Instructor does not have permission to add grades to this course.");
//        }
//
//        grade.setCourse(course);
//        gradeRepository.save(grade);
//    }

    // Remove Grade
//    public void removeGrade(Long instructorId, Grade grade) {
//        Course course = courseRepository.findById(grade.getCourse().getId())
//                .orElseThrow(() -> new IllegalArgumentException("Course not found"));
//        if (!course.getInstructor().getId().equals(instructorId)) {
//            throw new PermissionDeniedException("Instructor does not have permission to remove grades from this course.");
//        }
//
//        gradeRepository.delete(grade);
//    }

    public void addAssignmentGrade(Long instructorId, AssignmentGrade grade) {
        // Fetch the instructor
        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new IllegalArgumentException("Instructor not found"));

        // Fetch the assignment
        Assignment assignment = assignmentRepository.findById(grade.getAssignmentId())
                .orElseThrow(() -> new IllegalArgumentException("Assignment not found"));

        // Fetch the course associated with the assignment
        Course course = assignment.getCourse();

        // Ensure the instructor owns the course
        if (!course.getInstructor().getId().equals(instructorId)) {
            throw new PermissionDeniedException("Instructor does not have permission to add grades to this course.");
        }
        grade.setTitle(assignment.getTitle() + " grade");
        // Save the grade
        AssignmentGradeRepository.save(grade);
    }



//    public void removeGrade(Long instructorId, AssignmentGrade grade) {
//        // Validate the courseId
//        Course course = courseRepository.findById(grade.getCourseId())
//                .orElseThrow(() -> new IllegalArgumentException("Course not found"));
//
//        // Ensure the instructor owns the course
//        if (!course.getInstructor().getId().equals(instructorId)) {
//            throw new PermissionDeniedException("Instructor does not have permission to remove grades from this course.");
//        }
//
//        // Remove the grade
//        AssignmentGradeRepository.delete(grade);
//    }



    public List<Instructor> getAllInstructors() {
        return instructorRepository.findAll();
    }

    public List<Student> getEnrolledStudents(Long courseId) {
        return enrollmentRepository.findAll()
                .stream()
                .filter(enrollment -> enrollment.getCourse().getId().equals(courseId))
                .map(Enrollment::getStudent)
                .toList();
    }

    public void removeStudentFromCourse(Long instructorId, Long courseId, Long studentId) {
        // Fetch the course
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        // Ensure the instructor owns the course
        if (!course.getInstructor().getId().equals(instructorId)) {
            throw new PermissionDeniedException("Instructor does not have permission to modify this course.");
        }

        // Fetch the enrollment
        Enrollment enrollment = enrollmentRepository.findByCourseAndStudentId(course, studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student is not enrolled in this course."));

        // Remove the enrollment
        enrollmentRepository.delete(enrollment);
    }

}
