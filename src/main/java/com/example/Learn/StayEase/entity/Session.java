package com.example.Learn.StayEase.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sessionId;

    private String refreshToken;

    @Builder.Default
    private LocalDateTime expiryDate = LocalDateTime.now().plusDays(180);

    @ManyToOne
    private User user;

}
