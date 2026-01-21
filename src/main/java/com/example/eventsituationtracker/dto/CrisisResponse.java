package com.example.eventsituationtracker.dto;

import com.example.eventsituationtracker.domain.Crisis;
import com.example.eventsituationtracker.domain.CrisisStatus;
import com.example.eventsituationtracker.domain.CrisisType;
import com.example.eventsituationtracker.domain.CrisisPriority;
import java.time.Instant;
import java.util.UUID;

public class CrisisResponse {

    private UUID id;
    private CrisisType type;
    private CrisisStatus status;
    private CrisisPriority priority;
    private String title;
    private String description;
    private String location;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant resolvedAt;
    private Instant closedAt;
    private Double latitude;
    private Double longitude;
    private int eventCount;

    public CrisisResponse(Crisis crisis) {
        this.id = crisis.getId();
        this.type = crisis.getType();
        this.status = crisis.getStatus();
        this.priority = crisis.getPriority();
        this.title = crisis.getTitle();
        this.description = crisis.getDescription();
        this.location = crisis.getLocation();
        this.createdAt = crisis.getCreatedAt();
        this.updatedAt = crisis.getUpdatedAt();
        this.resolvedAt = crisis.getResolvedAt();
        this.closedAt = crisis.getClosedAt();
        this.latitude = crisis.getLatitude();
        this.longitude = crisis.getLongitude();
        this.eventCount = crisis.getEvents().size();
    }

    // Getters
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
    public int getEventCount() { return eventCount; }
}
