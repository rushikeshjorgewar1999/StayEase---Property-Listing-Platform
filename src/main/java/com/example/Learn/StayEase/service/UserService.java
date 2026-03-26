package com.example.Learn.StayEase.service;

import com.example.Learn.StayEase.dto.UserDTO;
import com.example.Learn.StayEase.entity.User;
import com.example.Learn.StayEase.exceptions.UserNotFoundException;
import com.example.Learn.StayEase.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public List<UserDTO> fetchAllUsers() {
        List<User> all = userRepository.findAll();
        if(all.isEmpty())
            return List.of();
        return all.stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }

    public UserDTO getUserProfileById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return modelMapper.map(user, UserDTO.class);
    }

    public UserDTO saveUser(User user) {
        User saved = userRepository.save(user);
        UserDTO userDto = modelMapper.map(saved, UserDTO.class);
        return userDto;
    }

}
