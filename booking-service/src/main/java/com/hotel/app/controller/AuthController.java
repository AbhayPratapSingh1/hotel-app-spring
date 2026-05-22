package com.hotel.app.controller;

import com.hotel.app.execptions.InvalidCredential;
import com.hotel.app.services.AuthService;
import com.hotel.app.services.JwtService;
import com.hotel.app.views.RegisterRequest;
import com.hotel.app.views.UserDetails;
import com.hotel.app.views.loginRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.constant.ConstantDesc;

@RestController
@RequestMapping("/api/users")
public class AuthController {

    private final AuthService authService;

    @Autowired
    private JwtService jwtService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody loginRequest loginRequest, HttpServletResponse response) {
        UserDetails userDetails = null;

        try {
            userDetails = authService.validate(loginRequest);
        } catch (InvalidCredential e) {
            return ResponseEntity.status(401).build();
        }

        ConstantDesc token = jwtService.generateToken(userDetails.userId());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {
        UserDetails userDetails = authService.addUser(registerRequest);
        return ResponseEntity.ok(userDetails);
    }
}
