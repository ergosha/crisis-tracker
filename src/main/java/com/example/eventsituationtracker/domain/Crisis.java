package com.example.eventsituationtracker.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(
    name = "crises",
    indexes = {
        @Index(name = "idx_crisis_status", columnList = "status"),
        @Index(name = "idx_crisis_priority", columnList = "priority"),
        @Index(name = "idx_crisis_location", columnList = "location"),
        @Index(name = "idx_crisis_created", columnList = "created_at")
    }
)
public class Crisis {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "crisis_type", nullable = false)
    private CrisisType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CrisisStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CrisisPriority priority;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private String location;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "resolved_at")
    private Instant resolvedAt;

    @Column(name = "closed_at")
    private Instant closedAt;

    // Koordinaatit
    private Double latitude;
    private Double longitude;

    // Events liittyvät tähän kriisin
    @OneToMany(mappedBy = "crisis", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Event> events = new ArrayList<>();

    protected Crisis() {
        // JPA only
    }

    public Crisis(
        CrisisType type,
        CrisisPriority priority,
        String title,
        String location,
        String description
    ) {
        this.type = type;
        this.priority = priority;
        this.status = CrisisStatus.OPEN;
        this.title = title;
        this.location = location;
        this.description = description;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    // Getterit
    public UUID getId() { return id; }
    public CrisisType getType() { return type; }
    public CrisisStatus getStatus() { return status; }
    public CrisisPriority getPriority() { return priority; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getLocation() { return location; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public Instant getResolvedAt() { return resolvedAt; }
    public Instant getClosedAt() { return closedAt; }
    public Double getLatitude() { return latitude; }
    public Double getLongitude() { return longitude; }
    public List<Event> getEvents() { return new ArrayList<>(events); }

    // Setterit
    public void setStatus(CrisisStatus status) {
        this.status = status;
        this.updatedAt = Instant.now();
        if (status == CrisisStatus.RESOLVED && this.resolvedAt == null) {
            this.resolvedAt = Instant.now();
        }
        if (status == CrisisStatus.CLOSED && this.closedAt == null) {
            this.closedAt = Instant.now();
        }
    }

    public void setPriority(CrisisPriority priority) {
        this.priority = priority;
        this.updatedAt = Instant.now();
    }

    public void setDescription(String description) {
        this.description = description;
        this.updatedAt = Instant.now();
    }

    public void setCoordinates(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.updatedAt = Instant.now();
    }

    // Event-hallinta
    public void addEvent(Event event) {
        event.setCrisis(this);
        events.add(event);
    }
}