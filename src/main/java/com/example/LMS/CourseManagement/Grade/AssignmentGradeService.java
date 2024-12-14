package com.example.LMS.CourseManagement.Grade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AssignmentGradeService {

    @Autowired
    private AssignmentGradeRepository assignmentGradeRepository;

    public List<AssignmentGrade> getAllGrades() {
        return assignmentGradeRepository.findAll();
    }

    public Optional<AssignmentGrade> getGradeById(Long id) {
        return assignmentGradeRepository.findById(id);
    }

    public AssignmentGrade createGrade(AssignmentGrade grade) {
        return assignmentGradeRepository.save(grade);
    }

    public AssignmentGrade updateGrade(Long id, AssignmentGrade updatedGrade) {
        return assignmentGradeRepository.findById(id).map(grade -> {
            grade.setStudentId(updatedGrade.getStudentId());
            grade.setAssignmentId(updatedGrade.getAssignmentId());
            grade.setTitle(updatedGrade.getTitle());
            grade.setGrade(updatedGrade.getGrade());
            return assignmentGradeRepository.save(grade);
        }).orElseThrow(() -> new RuntimeException("Grade not found with id: " + id));
    }

    public void deleteGrade(Long id) {
        assignmentGradeRepository.deleteById(id);
    }
}
