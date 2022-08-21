package com.spreadsearch.controllers;

import com.spreadsearch.payload.request.LoginRequest;
import com.spreadsearch.payload.request.SingUpRequest;
import com.spreadsearch.payload.response.JWTTokenSuccessResponse;
import com.spreadsearch.payload.response.MessageResponse;
import com.spreadsearch.security.JWTTokenProvider;
import com.spreadsearch.security.SecurityConstants;
import com.spreadsearch.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/auth")
@PreAuthorize("permitAll()")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JWTTokenProvider jwtTokenProvider;



    @PostMapping("/login")
    public ResponseEntity<Object> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = SecurityConstants.TOKEN_PREFIX + jwtTokenProvider.generateWebToken(authentication);
        return ResponseEntity.ok(new JWTTokenSuccessResponse(jwt, true));
    }


    @PostMapping("/registration")
    public ResponseEntity<Object> registerUser(@RequestBody SingUpRequest singUpRequest) {
        userService.createUser(singUpRequest);
        return ResponseEntity.ok(new MessageResponse("User registered successfully"));

    }
}

