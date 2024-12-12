package com.example.LMS.UserManagement.Instructor;

import com.example.LMS.CourseManagement.Assignment.Assignment;
import com.example.LMS.CourseManagement.Assignment.AssignmentRepository;
import com.example.LMS.CourseManagement.Course.Course;
import com.example.LMS.CourseManagement.Course.CourseRepository;
import com.example.LMS.CourseManagement.Grade.Grade;
import com.example.LMS.CourseManagement.Grade.GradeRepository;
import com.example.LMS.CourseManagement.Lesson.Lesson;
import com.example.LMS.CourseManagement.Lesson.LessonRepository;
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
    private final GradeRepository gradeRepository;

    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    public void addLesson(Long instructorId, Lesson lesson) {
        // Ensure the course exists and is owned by the instructor
        Course course = lesson.getCourse();

        if (course == null || !course.getInstructor().getId().equals(instructorId)) {
            throw new IllegalArgumentException("You do not have permission to add lessons to this course.");
        }

        // Save the lesson
        lessonRepository.save(lesson);
    }
    public void removeLesson(Lesson lesson) {
        lessonRepository.delete(lesson);
    }

    public void addAssignment(Long instructorId, Assignment assignment) {
        // Ensure the course exists and is owned by the instructor
        Course course = assignment.getCourse(); // Access the course directly from the Assignment

        if (course == null || !course.getInstructor().getId().equals(instructorId)) {
            throw new IllegalArgumentException("You do not have permission to add assignments to this course.");
        }

        // Save the assignment
        assignmentRepository.save(assignment);
    }
    public void removeAssignment(Assignment assignment) {
        assignmentRepository.delete(assignment);
    }

    public void addGrade(Long instructorId, Grade grade) {
        // Ensure the course exists and is owned by the instructor
        Course course = grade.getCourse();

        if (course == null || !course.getInstructor().getId().equals(instructorId)) {
            throw new IllegalArgumentException("You do not have permission to add grades to this course.");
        }

        // Save the grade
        gradeRepository.save(grade);
    }

    public void removeGrade(Grade grade) {
        gradeRepository.delete(grade);
    }

    public void updateCourse(Long courseId, Course updatedCourse) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));
        course.setTitle(updatedCourse.getTitle());
        course.setDescription(updatedCourse.getDescription());
        course.setDuration(updatedCourse.getDuration());
        courseRepository.save(course);
    }

    public List<Instructor> getAllInstructors() {
        return instructorRepository.findAll();
    }
}
