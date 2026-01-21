package com.example.eventsituationtracker.domain;

public enum CrisisEventType {
    DISPATCH,               // Yksikkö lähetetty paikalle
    ARRIVED,                // Yksikkö saapunut
    TREATING,               // Hoito käynnissä
    PATIENT_TRANSPORTED,    // Potilas kuljetettu
    DELIVERED,              // Toimitus/toimenpide valmis
    INCIDENT_CLOSED,        // Kriisi suljettu
    RESOURCE_REQUEST,       // Lisäresurssin pyyntö
    STATUS_UPDATE,          // Tilannepäivitys
    ESCALATION,             // Kriisi kohotettu prioriteetiksi
    DEESCALATION,           // Kriisi alennettu prioriteetiksi
    RESOURCE_REASSIGNMENT,  // Resurssi siirretty
    WEATHER_UPDATE,         // Sääpäivitys
    TRAFFIC_UPDATE          // Liikennetilanne
}
