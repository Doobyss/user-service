package com.aston.userservice.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserEventProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void send(UserEvent event) {
        String payload = String.format(
                "{\"operation\":\"%s\", \"email\":\"%s\"}",
                event.operation(), event.email()
        );

        kafkaTemplate.send("user-events", event.email(), payload);
    }
}