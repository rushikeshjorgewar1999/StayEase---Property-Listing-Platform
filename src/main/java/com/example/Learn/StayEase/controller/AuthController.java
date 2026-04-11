package com.example.Learn.StayEase.controller;

import com.example.Learn.StayEase.dto.AuthDTO;
import com.example.Learn.StayEase.dto.LoginDTO;
import com.example.Learn.StayEase.dto.LoginResponseDTO;
import com.example.Learn.StayEase.dto.UserDTO;
import com.example.Learn.StayEase.exceptions.ApiResponse;
import com.example.Learn.StayEase.service.AuthService;
import com.example.Learn.StayEase.service.JWTService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Arrays;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final JWTService jWTService;

    public AuthController(AuthService authService, JWTService jWTService) {
        this.authService = authService;
        this.jWTService = jWTService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginDTO loginDTO, HttpServletResponse response) {
        LoginResponseDTO loginResponseDTO = authService.login(loginDTO);
        Cookie cookie = new Cookie("jwt", loginResponseDTO.getRefreshToken());
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        return new ResponseEntity<>(loginResponseDTO, HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> signup(@Valid @RequestBody AuthDTO authDTO) {

        try {
            UserDTO userDTO = authService.signUpUserAndSave(authDTO);
            return new ResponseEntity<>(ApiResponse.builder().message("User registered successfully").status(HttpStatus.CREATED).build(), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(ApiResponse.builder().message(e.getMessage()).status(HttpStatus.BAD_REQUEST).build(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDTO> refresh(HttpServletRequest request) {
        String refreshToken = Arrays.stream(request.getCookies()).filter(cookie -> cookie.getName().equals("jwt")).map(Cookie::getValue).findFirst().orElseThrow(() -> new RuntimeException("refresh token not found in cookies"));
        LoginResponseDTO refresh = authService.refresh(refreshToken);
        refresh.setRefreshToken(refreshToken);
        Cookie cookie = new Cookie("jwt", refresh.getRefreshToken());
        cookie.setHttpOnly(true);

        return new ResponseEntity<>(refresh, HttpStatus.OK);
    }


}
