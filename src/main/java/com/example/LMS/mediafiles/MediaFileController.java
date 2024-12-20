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
            mediaFileService.uploadMedia(file, courseId);
            return ResponseEntity.ok("File uploaded successfully");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("File upload failed: " + e.getMessage());
        }
    }
}
