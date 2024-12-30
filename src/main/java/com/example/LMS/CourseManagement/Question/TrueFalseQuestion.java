package com.example.LMS.CourseManagement.Question;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrueFalseQuestion extends Question {

    private Boolean correctAnswer;  // Correct answer for T/F question

    @Override
    public boolean isCorrect(String answer) {
        return Boolean.parseBoolean(answer) == correctAnswer;  // Compare the student's answer with correct answer
    }
}