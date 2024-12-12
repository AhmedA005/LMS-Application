package com.example.LMS.CourseManagement.Course;

import com.example.LMS.CourseManagement.Lesson.Lesson;
import java.util.ArrayList;

public class Course {
    private int courseId;
    private String title;
    private String description;
    private ArrayList<Lesson> lessons;
    private ArrayList<Media> medias;

    public Course(int courseId, String title) {
        this.courseId = courseId;
        this.title = title;
        this.lessons = new ArrayList<>();
        this.medias = new ArrayList<>();
    }

    public int getCourseId() {
        return courseId;
    }

    public String getTitle() {
        return title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void addLesson(Lesson lesson) {
        lessons.add(lesson);
    }

    public Lesson getLesson(int id) {
        for (Lesson lesson : lessons) {
            if (lesson.getId() == id) return lesson;
        }
        return null;
    }

    public void uploadMedia(Media media) {
        medias.add(media);
    }

    public ArrayList<Media> getAllMedias() {
        return medias;
    }
}
