package com.example.Learn.StayEase;

import com.example.Learn.StayEase.handlers.CustomAuthenticationEntryPoint;
import com.example.Learn.StayEase.service.JWTService;
import com.example.Learn.StayEase.service.UserService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JWTAuthFilter extends OncePerRequestFilter {

    private final JWTService JWTService;
    private final UserService userService;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestToken = request.getHeader("Authorization");
        if (requestToken == null || !requestToken.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String token = requestToken.split("Bearer ")[1];
            String userEmailFromToken = JWTService.getUserEmailFromToken(token);
            if (userEmailFromToken != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userService.loadUserByUsername(userEmailFromToken);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (JwtException ex) {
            authenticationEntryPoint.commence(request, response, new BadCredentialsException(ex.getMessage(), ex));
            return;
        }

        filterChain.doFilter(request, response);
    }
}
