package com.example.eventsituationtracker.service;

import com.example.eventsituationtracker.domain.*;
import com.example.eventsituationtracker.repository.EventRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public void createEvent(Event event) {
        eventRepository.save(event);
    }

    public List<Event> getAllEvents() {
    return eventRepository.findAll();
    }

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
}

