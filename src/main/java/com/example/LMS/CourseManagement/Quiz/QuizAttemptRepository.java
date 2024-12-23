package com.example.LMS.CourseManagement.Quiz;

import com.example.LMS.UserManagement.Student.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuizAttemptRepository extends JpaRepository<QuizAttempt, Long> {
    QuizAttempt findByQuizAndStudent(Quiz quiz, Student student);

    List<QuizAttempt> findAllByStudentIdAndQuizIdIn(Long studentId, List<Long> quizIds);

    Optional<QuizAttempt> findByStudentIdAndQuizId(Long id, Long id1);

    Optional<QuizAttempt> findByStudentAndQuiz(Student student, Quiz quiz);
}
