package com.example.eventsituationtracker.domain;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
    name = "events",
    indexes = {
        @Index(name = "idx_event_location", columnList = "location"),
        @Index(name = "idx_event_type", columnList = "event_type")
    }
)
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Instant timestamp;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_type")
    private EventType type;

    @Enumerated(EnumType.STRING)
    private Severity severity;

    private String location;

    private String description;

    protected Event() {
        // JPA only
    }

    public Event(
        EventType type,
        Severity severity,
        String location,
        String description
    ) {
        this.timestamp = Instant.now();
        this.type = type;
        this.severity = severity;
        this.location = location;
        this.description = description;
    }

    public UUID getId() { return id; }
    public Instant getTimestamp() { return timestamp; }
    public EventType getType() { return type; }
    public Severity getSeverity() { return severity; }
    public String getLocation() { return location; }
    public String getDescription() { return description; }
}
