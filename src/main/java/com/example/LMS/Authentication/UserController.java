package com.example.LMS.Authentication;
 
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
        String response = service.addUser(userInfo); 
        return ResponseEntity.status(HttpStatus.CREATED).body(response); 
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