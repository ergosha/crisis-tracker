/*package com.example.eventsituationtracker.repository;

import com.example.eventsituationtracker.domain.Event;

import java.util.List;

public interface EventRepository {

    void save(Event event);

    List<Event> findAll();

} */

package com.example.eventsituationtracker.repository;

import com.example.eventsituationtracker.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {
}

