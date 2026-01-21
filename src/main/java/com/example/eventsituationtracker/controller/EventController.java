package com.example.eventsituationtracker.controller;

import com.example.eventsituationtracker.domain.Event;
import com.example.eventsituationtracker.dto.CreateEventRequest;
import com.example.eventsituationtracker.service.EventService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/crises/{crisisId}/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Event createEvent(
        @PathVariable UUID crisisId,
        @Valid @RequestBody CreateEventRequest request
    ) {
        Event event = new Event(
            request.getType(),
            request.getSeverity(),
            request.getDescription()
        );
        return eventService.createEvent(crisisId, event);
    }

    @GetMapping
    public List<Event> getEventsByCrisis(@PathVariable UUID crisisId) {
        return eventService.getEventsByCrisis(crisisId);
    }

    @GetMapping("/{eventId}")
    public Event getEventById(@PathVariable UUID eventId) {
        return eventService.getEventById(eventId);
    }
}

