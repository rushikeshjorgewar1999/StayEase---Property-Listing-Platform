package com.example.Learn.StayEase.service;

import com.example.Learn.StayEase.dto.AuthDTO;
import com.example.Learn.StayEase.dto.LoginDTO;
import com.example.Learn.StayEase.dto.LoginResponseDTO;
import com.example.Learn.StayEase.dto.UserDTO;
import com.example.Learn.StayEase.entity.User;
import com.example.Learn.StayEase.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {


    private final UserService userService;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final PasswordEncoder passwordEncoder;

    public UserDTO signUpUserAndSave(AuthDTO authDTO) {

        Optional<User> byEmail = userRepository.findByEmail(authDTO.getEmail());
        if(byEmail.isPresent())
            throw new BadCredentialsException("User is already present for email: " + authDTO.getEmail());

        User map = modelMapper.map(authDTO, User.class);
        map.setPassword(passwordEncoder.encode(authDTO.getPassword()));
        return userService.saveUser(map);
    }

    public LoginResponseDTO login(LoginDTO loginDTO) {

        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
        User user = (User) authenticate.getPrincipal();
        assert user != null;
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return new LoginResponseDTO(user.getEmail(), accessToken, refreshToken);
    }

    public LoginResponseDTO refresh(String refreshToken) {
        String userEmailFromToken = jwtService.getUserEmailFromToken(refreshToken);
        User user = userRepository.findByEmail(userEmailFromToken).orElseThrow(() -> new RuntimeException("user not found"));
        String accessToken = jwtService.generateAccessToken(user);
        return new LoginResponseDTO(user.getEmail(), accessToken, refreshToken);
    }

}
