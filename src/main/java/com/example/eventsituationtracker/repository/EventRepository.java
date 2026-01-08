package com.example.eventsituationtracker.repository;

import com.example.eventsituationtracker.domain.Event;

import java.util.List;

public interface EventRepository {

    void save(Event event);

    List<Event> findAll();

} 
