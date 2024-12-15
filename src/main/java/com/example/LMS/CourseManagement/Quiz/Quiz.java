package com.example.LMS.CourseManagement.Quiz;

import com.example.LMS.CourseManagement.Course.Course;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    @Getter
    private String title;
    private Date createdDate;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
    private List<QuizAttempt> attempts;
    public  Quiz(Long id ){
        this.id = id;
    }

    public Quiz() {

    }

    public Object getCourse() {
        return this.course;
    }

    public void setCourse(Object course) {
        this.course = (Course) course;
    }
}
