package com.example.eventsituationtracker.dto;

import com.example.eventsituationtracker.domain.CrisisStatus;
import com.example.eventsituationtracker.domain.CrisisPriority;

public class UpdateCrisisRequest {

    private CrisisStatus status;
    private CrisisPriority priority;
    private String description;

    public CrisisStatus getStatus() { return status; }
    public void setStatus(CrisisStatus status) { this.status = status; }

    public CrisisPriority getPriority() { return priority; }
    public void setPriority(CrisisPriority priority) { this.priority = priority; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
