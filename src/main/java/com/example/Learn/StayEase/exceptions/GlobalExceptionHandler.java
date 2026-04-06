package com.example.Learn.StayEase.exceptions;


import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse> handleAuthenticationException(AuthenticationException ex) {
        ApiResponse apiResponse = ApiResponse.builder().message(ex.getMessage()).status(HttpStatus.UNAUTHORIZED).build();
        return new ResponseEntity<>(apiResponse, HttpStatus.UNAUTHORIZED);
    }


}
