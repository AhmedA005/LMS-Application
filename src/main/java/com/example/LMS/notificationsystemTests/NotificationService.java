package com.example.LMS.notificationsystemTests;

import com.example.LMS.UserManagement.Instructor.Instructor;
import com.example.LMS.UserManagement.Instructor.InstructorRepository;
import com.example.LMS.UserManagement.Student.Student;
import com.example.LMS.UserManagement.Student.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final StudentRepository studentRepository;
    private final InstructorRepository instructorRepository;
    private final JavaMailSender mailSender;  // Inject JavaMailSender, not JavaMailSenderImpl

    public void sendNotificationToStudent(String message, Student student) {
        Notification notification = new Notification(message, student, null);
        notificationRepository.save(notification);
        sendEmailNotification(student.getId(), message);
    }

    public void sendNotificationToInstructor(String message, Instructor instructor) {
        Notification notification = new Notification(message, null, instructor);
        notificationRepository.save(notification);
        sendEmailNotification(instructor.getId(), message);
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

    public String getEmailForUser(Long userId) {
        Optional<Student> studentOptional = studentRepository.findById(userId);
        if(!studentOptional.isPresent()) {
            Optional<Instructor> instructorOptional = instructorRepository.findById(userId);
            return instructorOptional.map(Instructor::getEmail).orElse(null);
        }
        return studentOptional.map(Student::getEmail).orElse(null);
    }

    public String sendEmailNotification(Long userId, String message) {
        String email = getEmailForUser(userId);

        if (email != null) {
            try {
                SimpleMailMessage mailMessage = new SimpleMailMessage();
                mailMessage.setTo(email);
                mailMessage.setSubject("Notification");
                mailMessage.setText(message);
                mailSender.send(mailMessage);
                return "Notification sent successfully to your email.";
            } catch (Exception e) {
                return "Error sending email notification: " + e.getMessage();
            }
        }
        return "The email is not correct, please try again.";
    }
}
