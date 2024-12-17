package com.example.LMS.CourseManagement.Question;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MCQQuestion extends Question {

    @ElementCollection
    private List<String> options;  // List of options for MCQ

    private String correctAnswer;  // Correct option for the MCQ

    @Override
    public boolean isCorrect(String answer) {
        return answer.equals(correctAnswer);  // Check if student's answer matches the correct answer
    }
}
