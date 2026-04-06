package com.example.Learn.StayEase.dto;

import com.example.Learn.StayEase.constants.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthDTO {

    @NotBlank(message = "First name cannot be blank")
    private String firstName;
    private String lastName;
    private String userName;
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Please enter a valid email address")
    private String email;
    @NotBlank(message = "Password cannot be blank")
    private String password;
    private Role role;
    private Long phone;

}
