package com.example.LMS.notificationsystem;

import com.example.LMS.UserManagement.Instructor.Instructor;
import com.example.LMS.UserManagement.Student.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByStudent(Student student);
    List<Notification> findByInstructor(Instructor instructor);
}