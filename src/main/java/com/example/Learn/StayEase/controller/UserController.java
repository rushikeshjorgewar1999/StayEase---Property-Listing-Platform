package com.example.Learn.StayEase.controller;

import com.example.Learn.StayEase.dto.UserDTO;
import com.example.Learn.StayEase.entity.User;
import com.example.Learn.StayEase.exceptions.ApiResponse;
import com.example.Learn.StayEase.exceptions.UserNotFoundException;
import com.example.Learn.StayEase.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.fetchAllUsers();
        if(users.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity(users, HttpStatus.OK);
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<?> getProfileById(@PathVariable Long id) {
        try {
            UserDTO userProfileById = userService.getUserProfileById(id);
            return new ResponseEntity<>(userProfileById,HttpStatus.OK);
        } catch (UserNotFoundException e) {
            ApiResponse apiResponse = ApiResponse.builder().message("user not found for id : " + id).build();
            apiResponse.setStatus(HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(apiResponse,HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<UserDTO> addUser(@RequestBody User user) {
        UserDTO userAdded = userService.saveUser(user);
        if(userAdded == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(userAdded, HttpStatus.CREATED);
    }

    @PutMapping("/profile/{id}")
    public ResponseEntity<UserDTO> updateUser(@RequestBody User user, @PathVariable Long id) {
        UserDTO userUpdated = userService.updateUser(user,id);
        if(userUpdated == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(userUpdated, HttpStatus.OK);
    }

}
