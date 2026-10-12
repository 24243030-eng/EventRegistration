package com.example.eventregistration.api;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record RegistrationRequest(
        @NotBlank(message = "name is required") String name,
        @NotBlank(message = "email is required") @Email(message = "must be a valid email address") String email,
        @NotBlank(message = "eventName is required") String eventName,
        @Future(message = "must be a future date") LocalDate eventDate
) {
}