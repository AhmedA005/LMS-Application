package com.example.LMS.CourseManagement.Question;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShortAnswerQuestion extends Question {

    private String correctAnswer;  // Correct short answer

    @Override
    public boolean isCorrect(String answer) {
        return answer.equalsIgnoreCase(correctAnswer);  // Check if the student's answer matches the correct answer
    }
}

