package com.example.LMS.notificationsystemTests;

import com.example.LMS.UserManagement.Instructor.Instructor;
import com.example.LMS.UserManagement.Student.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public void sendNotificationToStudent(String message, Student student) {
        Notification notification = new Notification(message, student, null);
        notificationRepository.save(notification);
    }

    public void sendNotificationToInstructor(String message, Instructor instructor) {
        Notification notification = new Notification(message, null, instructor);
        notificationRepository.save(notification);
    }

    public List<Notification> getNotificationsForStudent(Student student) {
        List<Notification> notifications = notificationRepository.findByStudent(student);
        return notifications.isEmpty() ? null : notifications;
    }

    public List<Notification> getNotificationsForInstructor(Instructor instructor) {
        List<Notification> notifications = notificationRepository.findByInstructor(instructor);
        return notifications.isEmpty() ? null : notifications;
    }

    public void markAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new IllegalArgumentException("Notification not found"));
        notification.setRead(true);
        notificationRepository.save(notification);
    }
}