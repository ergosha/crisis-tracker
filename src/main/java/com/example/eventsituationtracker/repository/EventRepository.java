package com.example.eventsituationtracker.repository;

import com.example.eventsituationtracker.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.List;
import com.example.eventsituationtracker.domain.EventType;
import com.example.eventsituationtracker.domain.Severity;


@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {

    List<Event> findByLocation(String location);

    List<Event> findBySeverity(Severity severity);

    List<Event> findByType(EventType type);

}

