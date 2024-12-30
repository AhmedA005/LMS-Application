package com.example.LMS.Authentication;
import lombok.AllArgsConstructor; 
import lombok.Data; 
import lombok.NoArgsConstructor; 
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest { 
    private String username; 
    private String password; 
}
