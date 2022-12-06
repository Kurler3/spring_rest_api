package com.miguel.rest_api_spring.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
public class CustomAuthFilter extends UsernamePasswordAuthenticationFilter {

    // AUTH MANAGER
    private final AuthenticationManager authenticationManager;

    public CustomAuthFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        // GRAB USERNAME AND PASSWORD FROM PARAMS
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // LOG DATA
        log.info("Username is: {}", username); log.info("Password is: {}", password);

        // CREATE SIMPLE JWT TOKEN WITH CLASS IN SPRING SECURITY
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        // AUTHENTICATE WITH TOKEN
        return authenticationManager.authenticate(authenticationToken);
    }

    // SEND JWT AND REFRESH JWT WHEN SUCCESSFULLY LOGIN
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        // GET USER THAT WAS AUTHENTICATED
        User user = (User) authentication.getPrincipal();

        // DEFINE ALGORITHM (THIS SECRET WOULD COME FROM A HIDDEN SOURCE)
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());

        // CREATE ACCESS TOKEN
        String accessToken = JWT.create()
                // UNIQUE
                .withSubject(user.getUsername())
                // EXPIRATION
                .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                // ISSUER OF JWT
                .withIssuer(request.getRequestURL().toString())
                // CLAIMS
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                // SIGN
                .sign(algorithm);

        // CREATE REFRESH TOKEN
        String refreshToken = JWT.create()
                // UNIQUE
                .withSubject(user.getUsername())
                // EXPIRATION
                .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
                // ISSUER OF JWT
                .withIssuer(request.getRequestURL().toString())
                // SIGN
                .sign(algorithm);

        // SET HEADERS FOR FRONT-END
        response.setHeader("access_token", accessToken);
        response.setHeader("refresh_token", refreshToken);
    }
}
