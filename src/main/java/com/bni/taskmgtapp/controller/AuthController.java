package com.bni.taskmgtapp.controller;

import org.springframework.web.bind.annotation.RestController;

import com.bni.taskmgtapp.util.JwtUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class AuthController {
    
    private final JwtUtil jwtService;

    /**
     * Constructor for AuthController.
     * @param jwtService
     */
    public AuthController(JwtUtil jwtService) {
        this.jwtService = jwtService;
    }

    /**
     * Creates a JWT token for the given username.
     * The token is signed with the specified signing key.
     * @param username
     * @return
     */
    @GetMapping("/get-token/{username}")
    public String getToken(@PathVariable String username) {
        return jwtService.createToken(username);
    }

    /**
     * Validates the given JWT token.
     * @param token
     * @return
     */
    @PostMapping("/validate-token")
    public String validateToken(@RequestBody String token) {
        try {
            return "Token is valid for user: " + jwtService.validateToken(token);
        } catch (Exception e) {
            return "Token is invalid" + e.getMessage();
        }
    }

}
