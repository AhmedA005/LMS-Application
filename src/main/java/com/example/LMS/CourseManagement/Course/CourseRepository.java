package com.example.LMS.CourseManagement.Course;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    @Query("SELECT c FROM Course c JOIN FETCH c.instructor WHERE c.id = :courseId")
    Optional<Course> findByIdWithInstructor(@Param("courseId") Long courseId);
}
