package com.example.Learn.StayEase.exceptions;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(Long userId) {
        super("User not found with the given id : " + userId);
    }

}
