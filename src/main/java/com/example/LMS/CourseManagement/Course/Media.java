package com.example.LMS.CourseManagement.Media;

public class Media {
    private int id;
    private String url;

    public Media(int id, String url) {
        this.id = id;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }
}
