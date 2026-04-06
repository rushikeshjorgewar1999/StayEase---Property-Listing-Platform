package com.example.Learn.StayEase.handlers;

import com.example.Learn.StayEase.dto.UserDTO;
import com.example.Learn.StayEase.entity.User;
import com.example.Learn.StayEase.service.JWTService;
import com.example.Learn.StayEase.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserService userService;
    private final JWTService jWTService;

    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        DefaultOAuth2User user = (DefaultOAuth2User) oAuth2AuthenticationToken.getPrincipal();
        String email = user.getAttribute("email");
        User userByEmail = userService.getUserByEmail(email);
        if(userByEmail == null) {
            User saveUser = User.builder().firstName(user.getName()).email(email).build();
            userService.saveUser(saveUser);
        }

        String accessToken = jWTService.generateAccessToken(userByEmail);
        String refreshToken = jWTService.generateRefreshToken(userByEmail);

        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        String redirectUrl = "http://localhost:9090/home.html?token=" + accessToken;
        response.sendRedirect(redirectUrl);




    }

}
