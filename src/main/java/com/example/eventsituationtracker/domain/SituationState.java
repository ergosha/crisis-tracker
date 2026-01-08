package com.example.eventsituationtracker.domain;

import java.util.Map;

public class SituationState {

    private final Map<String, EventStatus> statusByLocation;

    public SituationState(Map<String, EventStatus> statusByLocation) {
        this.statusByLocation = statusByLocation;
    }

    public Map<String, EventStatus> getStatusByLocation() {
        return statusByLocation;
    }
}
