package com.example.Learn.StayEase.configs;

import com.example.Learn.StayEase.JWTAuthFilter;
import com.example.Learn.StayEase.constants.Permission;
import com.example.Learn.StayEase.constants.Role;
import com.example.Learn.StayEase.handlers.CustomAccessDeniedHandler;
import com.example.Learn.StayEase.handlers.CustomAuthenticationEntryPoint;
import com.example.Learn.StayEase.handlers.OAuth2SuccessHandler;
import com.example.Learn.StayEase.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {

    private final UserService userService;
    private JWTAuthFilter jwtAuthFilter;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;
    private final CustomAccessDeniedHandler accessDeniedHandler;

    @Autowired
    public WebSecurityConfig(UserService userService, JWTAuthFilter jwtAuthFilter, OAuth2SuccessHandler oAuth2SuccessHandler,
                             CustomAuthenticationEntryPoint authenticationEntryPoint, CustomAccessDeniedHandler accessDeniedHandler) {
        this.userService = userService;
        this.jwtAuthFilter = jwtAuthFilter;
        this.oAuth2SuccessHandler = oAuth2SuccessHandler;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws RuntimeException {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.
                authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/property/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/property/**").hasAnyRole( Role.GUEST.name(), Permission. USER_READ.name())
                        .anyRequest().authenticated())
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler))
                .oauth2Login(oauth2config -> oauth2config.failureUrl("/login?error=true")
                         .successHandler(oAuth2SuccessHandler));

        return httpSecurity.build();
    }
}
