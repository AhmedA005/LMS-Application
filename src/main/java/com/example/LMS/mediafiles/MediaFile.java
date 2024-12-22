package com.example.LMS.mediafiles;

import com.example.LMS.CourseManagement.Course.Course;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MediaFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;
    private String fileType;
    private String filePath;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course; // Each media file belongs to one course
}
