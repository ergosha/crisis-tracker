package com.example.eventsituationtracker.domain;

import java.time.Instant;
import java.util.UUID;

public class Event {
    
    private final UUID id;
    private final Instant timestamp;
    private final EventType type;
    private final Severity severity;
    private final String location;
    private final String description;

    public Event(
        EventType type,
        Severity severity,
        String location,
        String description
    ) {
        this.id = UUID.randomUUID();
        this.timestamp = Instant.now();
        this.type = type;
        this.severity = severity;
        this.location = location;
        this.description = description;
    }

    public UUID getId() {
        return id;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public EventType getType() {
        return type;
    }

    public Severity getSeverity() {
        return severity;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }
}
