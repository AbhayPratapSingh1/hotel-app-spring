package com.hotel.app.controller;

import com.hotel.app.execptions.InvalidCredential;
import com.hotel.app.services.AuthService;
import com.hotel.app.views.RegisterRequest;
import com.hotel.app.views.UserDetails;
import com.hotel.app.views.loginRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class AuthController {

    private final AuthService authService;

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

        Cookie sessionId = new Cookie("sessionId", userDetails.username());
        response.addCookie(sessionId);
        return ResponseEntity.ok("Logged in successfully");
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {
        UserDetails userDetails = authService.addUser(registerRequest);
        return ResponseEntity.ok(userDetails);
    }
}
