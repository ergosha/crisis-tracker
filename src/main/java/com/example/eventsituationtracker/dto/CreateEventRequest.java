package com.example.eventsituationtracker.dto;

import com.example.eventsituationtracker.domain.EventType;
import com.example.eventsituationtracker.domain.Severity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateEventRequest {
    
    @NotNull
    private EventType type;

    @NotNull
    private Severity severity;

    @NotBlank
    private String location;

    @NotBlank
    private String description;

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public Severity getSeverity() {
        return severity;
    }

    public void setSeverity(Severity severity) {
        this.severity = severity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
