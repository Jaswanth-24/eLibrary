package com.elibrary.eLibrary.model;


public class LoginRequest {

    private String email;
    private String password;

    // Default constructor (needed by Spring)
    public LoginRequest() {}

    // All-args constructor (optional)
    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Getters and setters
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
