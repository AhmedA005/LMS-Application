package com.example.LMS.mediafiles;

import com.example.LMS.CourseManagement.Course.Course;
import com.example.LMS.CourseManagement.Course.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class MediaFileService {
    private final Path mediaStorageLocation = Paths.get("uploads").toAbsolutePath().normalize();
    private final MediaFileRepository mediaFileRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public MediaFileService(MediaFileRepository mediaFileRepository, CourseRepository courseRepository) throws IOException {
        this.mediaFileRepository = mediaFileRepository;
        this.courseRepository = courseRepository;
        Files.createDirectories(mediaStorageLocation);
    }

    public Mediafiles uploadMedia(MultipartFile file, Long courseId) throws IOException {
        String fileName = file.getOriginalFilename();
        String fileType = file.getContentType();
        Path targetLocation = mediaStorageLocation.resolve(fileName);
        Files.copy(file.getInputStream(), targetLocation);

        Course course = courseRepository.findById(courseId).orElseThrow(() -> new IllegalArgumentException("Invalid Course ID"));

        Mediafiles mediaFile = new Mediafiles();
        mediaFile.setFileName(fileName);
        mediaFile.setFileType(fileType);
        mediaFile.setFilePath(targetLocation.toString());
        mediaFile.setCourse(course);

        return mediaFileRepository.save(mediaFile);
    }
}
