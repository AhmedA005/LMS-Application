package com.example.LMS.Authentication;
import org.springframework.data.jpa.repository.JpaRepository; 
import org.springframework.stereotype.Repository;
import com.example.LMS.Authentication.UserInfo;
import java.util.Optional; 
  
@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> { 
    Optional<UserInfo> findByName(String username);
    Optional<UserInfo> findByEmail(String email);
}