package com.example.LMS.Authentication;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.LMS.Authentication.UserInfo;
import com.example.LMS.Authentication.UserInfoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserInfoService implements UserDetailsService {
    private final UserInfoRepository repository;
    private final PasswordEncoder encoder;

    public UserInfoService(UserInfoRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Looking for user: " + username);
        Optional<UserInfo> userDetail = repository.findByName(username);

        if (userDetail.isEmpty()) {
            System.out.println("User not found: " + username);
        }

        return userDetail.map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }


    public String addUser(UserInfo userInfo) {
        userInfo.setPassword(encoder.encode(userInfo.getPassword()));
        if(repository.findByEmail(userInfo.getEmail()).isPresent()) {
            System.out.println("Email already exists");
            return "Email already exists";
        }
        repository.save(userInfo);
        return "User Added Successfully";
    }

    public List<UserInfo> getAllUsers() {
        return repository.findAll();
    }
}