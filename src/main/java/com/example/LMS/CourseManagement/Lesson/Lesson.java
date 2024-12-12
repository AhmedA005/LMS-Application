package com.example.LMS.CourseManagement.Lesson;

public class Lesson{
    private int id;
    private String title;
    Lesson(int id,String title){
        this.id=id;
        this.title=title;
    }
    public int getId() {
        return id;
    }



    public String getTitle() {
        return title;
    }




}