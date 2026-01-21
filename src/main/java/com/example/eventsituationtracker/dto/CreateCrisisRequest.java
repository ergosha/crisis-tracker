package com.example.eventsituationtracker.dto;

import com.example.eventsituationtracker.domain.CrisisType;
import com.example.eventsituationtracker.domain.CrisisPriority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateCrisisRequest {

    @NotNull
    private CrisisType type;

    @NotNull
    private CrisisPriority priority;

    @NotBlank
    private String title;

    @NotBlank
    private String location;

    private String description;
    private Double latitude;
    private Double longitude;

    // Getters ja setters
    public CrisisType getType() { return type; }
    public void setType(CrisisType type) { this.type = type; }

    public CrisisPriority getPriority() { return priority; }
    public void setPriority(CrisisPriority priority) { this.priority = priority; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }
}
