package com.example.eventsituationtracker.repository;

import com.example.eventsituationtracker.domain.Event;
import com.example.eventsituationtracker.domain.CrisisEventType;
import com.example.eventsituationtracker.domain.Severity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.List;


@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {

    List<Event> findByCrisisId(UUID crisisId);

    List<Event> findByType(CrisisEventType type);

    List<Event> findBySeverity(Severity severity);
}

