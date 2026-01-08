package com.example.eventsituationtracker.repository;

import com.example.eventsituationtracker.domain.Event;

import java.util.ArrayList;
import java.util.List;

public class InMemoryEventRepository implements EventRepository {

    private final List<Event> events = new ArrayList<>();

    @Override
    public void save(Event event) {
        events.add(event);
    }

    @Override
    public List<Event> findAll() {
        return List.copyOf(events);
    }
}
