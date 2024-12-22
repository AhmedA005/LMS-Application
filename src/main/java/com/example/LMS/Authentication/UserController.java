package com.example.LMS.Authentication;
 
import com.example.LMS.UserManagement.Admin.Admin;
import com.example.LMS.UserManagement.Instructor.Instructor;
import com.example.LMS.UserManagement.Student.Student;
import com.example.LMS.UserManagement.Student.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager; 
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken; 
import org.springframework.security.core.Authentication; 
import org.springframework.security.core.userdetails.UsernameNotFoundException; 
import org.springframework.web.bind.annotation.*;
import com.example.LMS.Authentication.AuthRequest;
import com.example.LMS.Authentication.UserInfo;
import com.example.LMS.JWT.JwtService;
import com.example.LMS.Authentication.UserInfoService;

import java.util.List;

@RestController
@RequestMapping("/auth") 
public class UserController { 
    private final UserInfoService service; 
    private final JwtService jwtService; 
    private final AuthenticationManager authenticationManager;
    private final StudentService studentService;
    UserController(UserInfoService service, JwtService jwtService, AuthenticationManager authenticationManager, StudentService studentService) {
        this.service = service; 
        this.jwtService = jwtService; 
        this.authenticationManager = authenticationManager;
        this.studentService = studentService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> addNewUser(@RequestBody UserInfo userInfo) {
        try {
            UserInfo user;
            switch (userInfo.getRole()) {
                case ADMIN:
                    user = new Admin(userInfo.getName(), userInfo.getPassword(),
                            userInfo.getEmail(), userInfo.getRole());
                    break;
                case STUDENT:
                    user = new Student(userInfo.getName(), userInfo.getPassword(),
                            userInfo.getEmail(), userInfo.getRole());
                    break;
                case INSTRUCTOR:
                    user = new Instructor(userInfo.getName(), userInfo.getPassword(),
                            userInfo.getEmail(), userInfo.getRole());
                    break;
                default:
                    throw new IllegalArgumentException("Invalid role");
            }

            String response = service.addUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error during registration: " + e.getMessage());
        }
    }
  
    @PostMapping("/generateToken") 
    public ResponseEntity<String> authenticateAndGetToken(@RequestBody AuthRequest authRequest) { 
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())); 
        if (authentication.isAuthenticated()) { 
            String token = jwtService.generateToken(authRequest.getUsername());
            return ResponseEntity.ok(token); 
        } else { 
            throw new UsernameNotFoundException("Invalid user request!"); 
        } 
    } 
    @GetMapping("/hello")
    public String hello() {
        return "Hello World!";
    }

  
}