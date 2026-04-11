package com.example.Learn.StayEase.service;

import com.example.Learn.StayEase.entity.Session;
import com.example.Learn.StayEase.entity.User;
import com.example.Learn.StayEase.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
public class SessionService {

    @Autowired
    private SessionRepository sessionRepository;
    private final int MAX_SESSIONS_PER_USER = 2;

    public void createNewSession(User user, String refreshToken) {

        List<Session> userSessions = sessionRepository.findSessionByUser(user);
        if(userSessions.size() == MAX_SESSIONS_PER_USER) {
            userSessions.sort(Comparator.comparing(Session::getExpiryDate));

            Session expiredSession = userSessions.getFirst();
            sessionRepository.delete(expiredSession);

        }

        Session newSession = Session.builder()
                .user(user)
                .refreshToken(refreshToken)
                .build();

        sessionRepository.save(newSession);

    }

    public void validateSession(String refreshToken) {
        Session session = sessionRepository.findByRefreshToken(refreshToken).orElseThrow(() -> new RuntimeException("Invalid refresh token"));
        if (session.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Refresh token session has expired, please login again");
        }
    }

}
