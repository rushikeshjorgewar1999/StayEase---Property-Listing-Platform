package com.example.Learn.StayEase.dto;

import com.example.Learn.StayEase.constants.Role;
import lombok.Data;

import java.util.Set;

@Data
public class UserDTO {

    private Long userId;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private Long phone;
    private Set<Role> role;

}
