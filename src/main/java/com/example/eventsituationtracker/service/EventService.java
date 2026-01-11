package com.example.eventsituationtracker.service;

import com.example.eventsituationtracker.domain.Event;
import com.example.eventsituationtracker.domain.EventStatus;
import com.example.eventsituationtracker.domain.SituationState;
import com.example.eventsituationtracker.repository.EventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public void createEvent(Event event) {
        validateEvent(event);
        eventRepository.save(event);
    }

    @Transactional(readOnly = true)
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @Transactional(readOnly = true)
    public SituationState deriveSituationState() {
        List<Event> events = eventRepository.findAll();

        Map<String, EventStatus> statusByLocation = new HashMap<>();

        for (Event event : events) {
            switch (event.getType()) {
                case ALERT -> statusByLocation.put(event.getLocation(), EventStatus.OPEN);
                case UPDATE -> statusByLocation.put(event.getLocation(), EventStatus.ACKNOWLEDGED);
                case RESOLVE -> statusByLocation.put(event.getLocation(), EventStatus.RESOLVED);
            }
        }

        return new SituationState(statusByLocation);
    }

    private void validateEvent(Event event) {
        if (event.getType() == null) {
            throw new IllegalArgumentException("Event type must be provided");
        }
        if (event.getSeverity() == null) {
            throw new IllegalArgumentException("Severity must be provided");
        }
        if (event.getLocation() == null || event.getLocation().isBlank()) {
            throw new IllegalArgumentException("Location must not be empty");
        }
    }
}


