package com.elibrary.eLibrary.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.elibrary.eLibrary.DTO.RegisterRequest;
import com.elibrary.eLibrary.entity.User;
import com.elibrary.eLibrary.repository.UserRepository;
import com.elibrary.eLibrary.security.JwtUtil;
import com.elibrary.eLibrary.model.LoginRequest;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtil jwt;

    // ================= LOGIN =================
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {

        User user = userRepo.findByEmail(req.getEmail()).orElse(null);

        if (user == null || !encoder.matches(req.getPassword(), user.getPassword())) {
            return ResponseEntity
                    .status(401)
                    .body(Map.of("message", "Invalid email or password"));
        }

        return ResponseEntity.ok(
                Map.of("token", jwt.generateToken(user.getEmail()),
                       "name", user.getName())
        );
    }

    // ================= REGISTER =================
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest request) {

        if (request.getName() == null || request.getName().isBlank()) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("message", "Name is required"));
        }

        if (request.getEmail() == null || request.getEmail().isBlank()) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("message", "Email is required"));
        }

        if (request.getPassword() == null || request.getPassword().isBlank()) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("message", "Password is required"));
        }

        String email = request.getEmail().trim().toLowerCase();

        if (userRepo.existsByEmail(email)) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("message", "Email already registered"));
        }

        User user = new User();
        user.setName(request.getName().trim());
        user.setEmail(email);
        user.setPassword(encoder.encode(request.getPassword()));

        userRepo.save(user);

        return ResponseEntity.ok(
                Map.of("message", "User registered successfully")
        );
    }
}
