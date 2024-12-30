package com.example.LMS.CourseManagement.Assignment;

import com.example.LMS.UserManagement.Student.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AssignmentAttemptRepository extends JpaRepository<AssignmentAttempt, Integer> {

    List<AssignmentAttempt> findAllByStudentIdAndAssignmentIdIn(Long studentId, List<Long> assignmentIds);

    Optional<AssignmentAttempt> findByStudentIdAndAssignmentId(Long id, Long id1);

    Optional<AssignmentAttempt> findByStudentAndAssignment(Student student, Assignment assignment);
}
