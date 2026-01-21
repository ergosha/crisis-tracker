package com.example.eventsituationtracker.dto;

import com.example.eventsituationtracker.domain.CrisisEventType;
import com.example.eventsituationtracker.domain.Severity;
import jakarta.validation.constraints.NotNull;

public class CreateEventRequest {

    @NotNull
    private CrisisEventType type;

    private Severity severity;
    private String description;

    public CrisisEventType getType() { return type; }
    public void setType(CrisisEventType type) { this.type = type; }

    public Severity getSeverity() { return severity; }
    public void setSeverity(Severity severity) { this.severity = severity; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
