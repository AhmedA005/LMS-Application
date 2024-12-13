package com.example.LMS.CourseManagement.Enrollment;

import com.example.LMS.CourseManagement.Course.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    List<Enrollment> findByCourse(Course course);
    Optional<Enrollment> findByCourseAndStudentId(Course course, Long studentId);

}

