package com.example.LMS.mediafiles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/media")
public class MediaFileController {

    @Autowired
    private MediaFileService mediaFileService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadMedia(
            @RequestParam("file") MultipartFile file,
            @RequestParam("courseId") Long courseId) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("File is empty. Please upload a valid file.");
            }

            // File type validation (optional)
            String fileType = file.getContentType();
            if (fileType == null || (!fileType.equals("application/pdf") && !fileType.equals("application/msword") && !fileType.startsWith("video/"))) {
                return ResponseEntity.badRequest().body("Invalid file type. Only PDFs, Word documents, and videos are allowed.");
            }

            Mediafiles media = mediaFileService.uploadMedia(file, courseId);
            return ResponseEntity.ok(
                    "File uploaded successfully:\n" +
                            "File Name: " + media.getFileName() + "\n" +
                            "File Type: " + media.getFileType() + "\n" +
                            "Course ID: " + courseId
            );
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid input: " + e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(500).body("File upload failed: " + e.getMessage());
        }
    }
}
