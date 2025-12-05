package com.aston.userservice.kafka;

public record UserEvent(
        String operation,
        String email
) {}