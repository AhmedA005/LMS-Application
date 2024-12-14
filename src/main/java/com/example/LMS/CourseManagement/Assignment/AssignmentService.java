package com.example.LMS.CourseManagement.Assignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AssignmentService {

    @Autowired
    private AssignmentRepository assignmentRepository;

    public List<Assignment> getAllAssignments() {
        return assignmentRepository.findAll();
    }

    public Optional<Assignment> getAssignmentById(Long id) {
        return assignmentRepository.findById(id);
    }

    public Assignment createAssignment(Assignment assignment) {
        return assignmentRepository.save(assignment);
    }

    public Assignment updateAssignment(Long id, Assignment updatedAssignment) {
        return assignmentRepository.findById(id).map(assignment -> {
            assignment.setTitle(updatedAssignment.getTitle());
            assignment.setDescription(updatedAssignment.getDescription());
            assignment.setCourse(updatedAssignment.getCourse());
            return assignmentRepository.save(assignment);
        }).orElseThrow(() -> new RuntimeException("Assignment not found with id: " + id));
    }

    public void deleteAssignment(Long id) {
        assignmentRepository.deleteById(id);
    }
}
