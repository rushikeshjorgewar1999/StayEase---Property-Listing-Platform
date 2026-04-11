package com.example.Learn.StayEase.repository;

import com.example.Learn.StayEase.entity.Session;
import com.example.Learn.StayEase.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {

    List<Session> findSessionByUser(User user);

    Optional<Session> findByRefreshToken(String refreshToken);

}
