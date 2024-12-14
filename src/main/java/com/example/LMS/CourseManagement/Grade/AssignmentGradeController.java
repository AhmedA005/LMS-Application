package com.example.LMS.CourseManagement.Grade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grades")
public class AssignmentGradeController {

    @Autowired
    private AssignmentGradeService assignmentGradeService;

    @GetMapping
    public List<AssignmentGrade> getAllGrades() {
        return assignmentGradeService.getAllGrades();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssignmentGrade> getGradeById(@PathVariable Long id) {
        return assignmentGradeService.getGradeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public AssignmentGrade createGrade(@RequestBody AssignmentGrade grade) {
        return assignmentGradeService.createGrade(grade);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssignmentGrade> updateGrade(
            @PathVariable Long id,
            @RequestBody AssignmentGrade updatedGrade) {
        try {
            AssignmentGrade updated = assignmentGradeService.updateGrade(id, updatedGrade);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGrade(@PathVariable Long id) {
        assignmentGradeService.deleteGrade(id);
        return ResponseEntity.noContent().build();
    }
}
