package com.example.eventsituationtracker.domain;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
    name = "events",
    indexes = {
        @Index(name = "idx_event_crisis", columnList = "crisis_id"),
        @Index(name = "idx_event_type", columnList = "event_type"),
        @Index(name = "idx_event_timestamp", columnList = "timestamp")
    }
)
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_type", nullable = false)
    private CrisisEventType type;

    @Enumerated(EnumType.STRING)
    private Severity severity;

    @Column(nullable = false)
    private Instant timestamp;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "crisis_id", nullable = false)
    private Crisis crisis;

    protected Event() {
        // JPA only
    }

    public Event(
        CrisisEventType type,
        Severity severity,
        String description
    ) {
        this.type = type;
        this.severity = severity;
        this.description = description;
        this.timestamp = Instant.now();
    }

    // Getterit
    public UUID getId() { return id; }
    public CrisisEventType getType() { return type; }
    public Severity getSeverity() { return severity; }
    public Instant getTimestamp() { return timestamp; }
    public String getDescription() { return description; }
    public Crisis getCrisis() { return crisis; }

    // Setterit
    public void setCrisis(Crisis crisis) { this.crisis = crisis; }
    public void setSeverity(Severity severity) { this.severity = severity; }
    public void setDescription(String description) { this.description = description; }
}
