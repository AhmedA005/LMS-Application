package com.example.LMS.UserManagement.Instructor;

import com.example.LMS.CourseManagement.Assignment.Assignment;
import com.example.LMS.CourseManagement.Assignment.AssignmentRepository;
import com.example.LMS.CourseManagement.Course.Course;
import com.example.LMS.CourseManagement.Course.CourseRepository;
import com.example.LMS.CourseManagement.Grade.Grade;
import com.example.LMS.CourseManagement.Grade.GradeRepository;
import com.example.LMS.CourseManagement.Lesson.Lesson;
import com.example.LMS.CourseManagement.Lesson.LessonRepository;
import com.example.LMS.PermissionDeniedException;
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
        // Fetch the authenticated instructor
        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new IllegalArgumentException("Instructor not found"));

        // Fetch the course
        Course course = courseRepository.findById(lesson.getCourse().getId())
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        // Ensure the instructor owns the course
        if (!course.getInstructor().getId().equals(instructorId)) {
            throw new PermissionDeniedException("Instructor does not have permission to add lessons to this course.");
        }

        lesson.setCourse(course);
        // Save the lesson
        lessonRepository.save(lesson);
    }
    public void removeLesson(Lesson lesson) {
        lessonRepository.delete(lesson);
    }

    public void addAssignment(Long instructorId, Assignment assignment) {
        // Fetch the authenticated instructor
        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new IllegalArgumentException("Instructor not found"));

        // Fetch the course
        Course course = courseRepository.findById(assignment.getCourse().getId())
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        // Ensure the instructor owns the course
        if (!course.getInstructor().getId().equals(instructorId)) {
            throw new PermissionDeniedException("Instructor does not have permission to add assignments to this course.");
        }

        // Link course to assignment
        assignment.setCourse(course);

        // Save the assignment
        assignmentRepository.save(assignment);
    }


    public void removeAssignment(Assignment assignment) {
        assignmentRepository.delete(assignment);
    }

    public void addGrade(Long instructorId, Grade grade) {
        // Fetch the authenticated instructor
        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new IllegalArgumentException("Instructor not found"));

        // Fetch the course
        Course course = courseRepository.findById(grade.getCourse().getId())
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        // Ensure the instructor owns the course
        if (!course.getInstructor().getId().equals(instructorId)) {
            throw new PermissionDeniedException("Instructor does not have permission to add grades to this course.");
        }

        grade.setCourse(course);

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
