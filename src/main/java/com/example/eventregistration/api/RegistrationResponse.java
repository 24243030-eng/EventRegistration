package com.example.eventregistration.api;

import java.time.LocalDate;
import java.util.UUID;

public record RegistrationResponse(UUID id, String name, String email, String eventName, LocalDate eventDate) {
}