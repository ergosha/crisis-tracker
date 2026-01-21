package com.example.eventsituationtracker.domain;

public enum CrisisStatus {
    OPEN,           // just opened, not yet addressed
    ONGOING,        // actively being handled
    RESOLVED,       // main problem fixed, monitoring situation
    CLOSED,         // completely finished
    ARCHIVED        // kept for records, no active management
}
