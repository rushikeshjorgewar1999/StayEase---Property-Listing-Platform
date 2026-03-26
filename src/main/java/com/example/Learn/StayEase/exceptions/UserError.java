package com.example.Learn.StayEase.exceptions;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class UserError {

    private String message;
    private HttpStatus status;
}
