package com.example.LMS.CourseManagement.Assignment;

import com.example.LMS.UserManagement.Student.Student;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentAttempt {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private Date attemptDate;
        private int score;
        private String feedback;

        @ManyToOne
        @JoinColumn(name = "assignment_id")
        private Assignment assignment;

        @ManyToOne
        @JoinColumn(name = "student_id")
        private Student student;

        private String answer;
        private boolean graded;

}
