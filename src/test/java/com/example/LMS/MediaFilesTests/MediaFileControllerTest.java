package com.example.LMS.MediaFilesTests;

import com.example.LMS.mediafiles.MediaFileController;
import com.example.LMS.mediafiles.MediaFileService;
import com.example.LMS.CourseManagement.Course.Course;
import com.example.LMS.CourseManagement.Course.CourseRepository;
import com.example.LMS.mediafiles.Mediafiles;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MediaFileControllerTest {

    @InjectMocks
    private MediaFileController mediaFileController;

    @Mock
    private MediaFileService mediaFileService;

    @Mock
    private CourseRepository courseRepository;

    @BeforeEach
    public void setUp() {
        // No additional setup needed in this case
    }

    @Test
    public void testUploadMediaSuccess() throws IOException {
        // Mock successful upload
        Course mockCourse = mock(Course.class);
        when(courseRepository.findById(1L)).thenReturn(java.util.Optional.of(mockCourse));
        when(mediaFileService.uploadMedia(any(MultipartFile.class), eq(1L))).thenReturn(new Mediafiles());

        // Perform upload request
        ResponseEntity<String> response = mediaFileController.uploadMedia(mockMultipartFile(), 1L);

        // Assert successful response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("File uploaded successfully", response.getBody());
    }

    @Test
    public void testUploadMediaFailure_InvalidCourseId() throws IOException {
        // Mock Course not found for invalid ID
        when(courseRepository.findById(100L)).thenReturn(java.util.Optional.empty());

        // Perform upload request
        ResponseEntity<String> response = mediaFileController.uploadMedia(mockMultipartFile(), 100L);

        // Assert failure response
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Invalid Course ID", response.getBody());
    }

    @Test
    public void testUploadMediaFailure_IOException() throws IOException {
        // Mock IOException during upload
        Course mockCourse = mock(Course.class);
        when(courseRepository.findById(1L)).thenReturn(java.util.Optional.of(mockCourse));
        when(mediaFileService.uploadMedia(any(MultipartFile.class), eq(1L))).thenThrow(new IOException("Test IOException"));

        // Perform upload request
        ResponseEntity<String> response = mediaFileController.uploadMedia(mockMultipartFile(), 1L);

        // Assert failure response
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("File upload failed: Test IOException", response.getBody());
    }

    private MultipartFile mockMultipartFile() {
        return new MockMultipartFile("test_file.txt", "test data".getBytes());
    }
}