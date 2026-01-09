package com.example.eventsituationtracker.controller;

import com.example.eventsituationtracker.domain.Event;
import com.example.eventsituationtracker.domain.SituationState;
import com.example.eventsituationtracker.dto.CreateEventRequest;
import com.example.eventsituationtracker.service.EventService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createEvent(@Valid @RequestBody CreateEventRequest request) {
        Event event = new Event(
                request.getType(),
                request.getSeverity(),
                request.getLocation(),
                request.getDescription()
        );

        eventService.createEvent(event);
    }

    @GetMapping
    public List<Event> getEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping("/situation")
    public SituationState getSituation() {
        return eventService.deriveSituationState();
    }
}

