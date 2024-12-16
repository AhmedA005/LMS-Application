package com.example.LMS.CourseManagement.Question;

import com.example.LMS.CourseManagement.Course.Course;
import com.example.LMS.CourseManagement.Quiz.Quiz;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)  // Use JOINED strategy for inheritance
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = MCQQuestion.class, name = "mcq"),
        @JsonSubTypes.Type(value = ShortAnswerQuestion.class, name = "shortAnswer"),
        @JsonSubTypes.Type(value = TrueFalseQuestion.class, name = "trueFalse")
})
public abstract class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String questionText;

    @ManyToOne
    @JoinColumn(name = "course_id")
    @JsonBackReference
    private Course course;


    // Abstract method for grading logic
    public abstract boolean isCorrect(String answer);
}
