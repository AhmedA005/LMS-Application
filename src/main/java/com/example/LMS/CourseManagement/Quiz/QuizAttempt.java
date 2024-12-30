package com.example.LMS.CourseManagement.Quiz;

import com.example.LMS.CourseManagement.Question.Question;
import com.example.LMS.UserManagement.Student.Student;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuizAttempt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date attemptDate;
    private int score;
    private String feedback;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "quiz_questions",
            joinColumns = @JoinColumn(name = "quiz_id"),
            inverseJoinColumns = @JoinColumn(name = "question_id")
    )
    @JsonManagedReference
    private List<Question> questions;

//    @ElementCollection
//    private List<Long> selectedQuestionIds; // Stores IDs of randomly selected questions
    @ElementCollection
    private List<String> studentAnswers; // Stores student answers


}
