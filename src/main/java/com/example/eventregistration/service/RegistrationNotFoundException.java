package com.example.eventregistration.service;

import java.util.UUID;

public class RegistrationNotFoundException extends RuntimeException {
    public RegistrationNotFoundException(UUID id) {
        super("Registration not found: " + id);
    }
}