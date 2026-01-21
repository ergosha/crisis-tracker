package com.example.eventsituationtracker.service;

import com.example.eventsituationtracker.domain.Event;
import com.example.eventsituationtracker.domain.Crisis;
import com.example.eventsituationtracker.domain.Severity;
import com.example.eventsituationtracker.repository.EventRepository;
import com.example.eventsituationtracker.repository.CrisisRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@Transactional
public class EventService {

    private final EventRepository eventRepository;
    private final CrisisRepository crisisRepository;

    public EventService(EventRepository eventRepository, CrisisRepository crisisRepository) {
        this.eventRepository = eventRepository;
        this.crisisRepository = crisisRepository;
    }

    public Event createEvent(UUID crisisId, Event event) {
        Crisis crisis = crisisRepository.findById(crisisId)
            .orElseThrow(() -> new NoSuchElementException("Crisis not found: " + crisisId));
        
        validateEvent(event);
        event.setCrisis(crisis);
        return eventRepository.save(event);
    }

    @Transactional(readOnly = true)
    public List<Event> getEventsByCrisis(UUID crisisId) {
        return eventRepository.findByCrisisId(crisisId);
    }

    @Transactional(readOnly = true)
    public List<Event> getEventsBySeverity(Severity severity) {
        return eventRepository.findBySeverity(severity);
    }

    @Transactional(readOnly = true)
    public Event getEventById(UUID id) {
        return eventRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Event not found: " + id));
    }

    private void validateEvent(Event event) {
        if (event.getType() == null) {
            throw new IllegalArgumentException("Event type must be provided");
        }
        if (event.getSeverity() == null) {
            throw new IllegalArgumentException("Severity must be provided");
        }
    }
}



