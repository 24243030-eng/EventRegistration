package com.example.eventregistration.service;

import com.example.eventregistration.api.RegistrationRequest;
import com.example.eventregistration.api.RegistrationResponse;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class RegistrationService {
    private final ConcurrentMap<UUID, RegistrationResponse> registrations = new ConcurrentHashMap<>();

    public RegistrationResponse register(RegistrationRequest request) {
        RegistrationResponse registration = new RegistrationResponse(
                UUID.randomUUID(), request.name(), request.email(), request.eventName(), request.eventDate());
        registrations.put(registration.id(), registration);
        return registration;
    }

    public List<RegistrationResponse> findAll() {
        return registrations.values().stream()
                .sorted(Comparator.comparing(RegistrationResponse::id))
                .toList();
    }

    public RegistrationResponse findById(UUID id) {
        RegistrationResponse registration = registrations.get(id);
        if (registration == null) {
            throw new RegistrationNotFoundException(id);
        }
        return registration;
    }
}