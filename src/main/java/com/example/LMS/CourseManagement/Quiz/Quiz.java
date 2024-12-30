package com.example.LMS.CourseManagement.Quiz;

import com.example.LMS.CourseManagement.Course.Course;
import com.example.LMS.CourseManagement.Question.MCQQuestion;
import com.example.LMS.CourseManagement.Question.Question;
import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    @Getter
    private String title;
    private Date createdDate;
    private QuizType quizType;
    private int numberOfQuestions;


    @ManyToOne
    @JoinColumn(name = "course_id")
    @JsonBackReference
    private Course course;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
    private List<QuizAttempt> attempts;


    public enum QuizType {
        MCQ, TrueFalse, ShortAnswer
    }
//    @Override
//    public String toString() {
//        String quiz = "Number of questions: " + numberOfQuestions + "\n" + quizType.toString();
//        for (Question question : questions) {
//            quiz += "\n" + question.getQuestionText();
//            if(question instanceof MCQQuestion)
//                quiz += "\n" + ((MCQQuestion) question).getOptions();
//            quiz += "\n";
//        }
//        return quiz;
//    }

}
