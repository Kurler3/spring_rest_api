package com.miguel.rest_api_spring.security;
import com.miguel.rest_api_spring.filter.CustomAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final AuthenticationManagerBuilder authManagerBuilder;


    @Bean
    public AuthenticationManager authenticationManagerBean(HttpSecurity http) throws Exception {

        // AUTHENTICATION MANAGER BUILDER
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);

        // USE USER DETAILS SERVICE
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);

        // RETURN AUTH MANAGER
        return authenticationManagerBuilder.build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // DISABLE CROSS ORIGIN
        http.csrf().disable();

        // DISABLE DEFAULT COOKIE SESSION MANAGEMENT
        http.sessionManagement().sessionCreationPolicy(STATELESS);

        // ALLOW ANYONE TO MAKE REQUESTS
        http.authorizeHttpRequests().anyRequest().permitAll();

        // FILTER USER WHEN LOGGING IN (CUSTOM AUTH FILTER)
        http.addFilter(new CustomAuthFilter(authManagerBuilder.getOrBuild()));

        return http.build();
    }



}
