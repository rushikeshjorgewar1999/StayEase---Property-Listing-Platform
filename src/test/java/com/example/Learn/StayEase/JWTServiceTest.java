package com.example.Learn.StayEase;

import com.example.Learn.StayEase.constants.Role;
import com.example.Learn.StayEase.entity.User;
import com.example.Learn.StayEase.service.JWTService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Date;

@SpringBootTest
public class JWTServiceTest {

    @Autowired
    private JWTService jwtService;

    @Test
    public void loadContext() {
        User user = new User(1L,"ranveer","singh","ranveer.singh@gmail.com","ranveer", Role.ADMIN,9238492080L, LocalDateTime.now(),LocalDateTime.now(),null);
        String token = jwtService.generateToken(user);
        System.out.println(token);

        String userEmailFromToken = jwtService.getUserEmailFromToken(token);
        System.out.println(userEmailFromToken);
    }

}
