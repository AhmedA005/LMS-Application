package com.example.LMS.mediafiles;

import com.example.LMS.CourseManagement.Course.Course;
import com.example.LMS.CourseManagement.Course.CourseRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class MediaFileService {

    private final Path mediaStorageLocation;
    private final MediaFileRepository mediaFileRepository;
    private final CourseRepository courseRepository;

    public MediaFileService(MediaFileRepository mediaFileRepository, CourseRepository courseRepository,
                            @Value("${file.upload-dir}") String uploadDir) throws IOException {
        this.mediaFileRepository = mediaFileRepository;
        this.courseRepository = courseRepository;

        // Set up the file storage directory
        this.mediaStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
        Files.createDirectories(this.mediaStorageLocation);
    }

    public Mediafiles uploadMedia(MultipartFile file, Long courseId) throws IOException {
        // Validate the file
        String fileType = file.getContentType();
        if (fileType == null || (!fileType.startsWith("image/") && !fileType.equals("application/pdf"))) {
            throw new IllegalArgumentException("Invalid file type. Only images and PDFs are allowed.");
        }

        // Generate a unique file name
        String fileName = System.currentTimeMillis() + "_" + StringUtils.cleanPath(file.getOriginalFilename());
        Path targetLocation = mediaStorageLocation.resolve(fileName);

        // Copy file to target location
        try {
            Files.copy(file.getInputStream(), targetLocation);
        } catch (IOException ex) {
            throw new IOException("Could not store file " + fileName + ". Please try again!", ex);
        }

        // Fetch the course
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Course ID"));

        // Save file metadata in the database
        Mediafiles mediaFile = new Mediafiles();
        mediaFile.setFileName(fileName);
        mediaFile.setFileType(fileType);
        mediaFile.setFilePath(targetLocation.toString());
        mediaFile.setCourse(course);

        return mediaFileRepository.save(mediaFile);
    }
}
