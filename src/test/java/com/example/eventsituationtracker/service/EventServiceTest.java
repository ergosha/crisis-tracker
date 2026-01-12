package com.example.eventsituationtracker.service;
import com.example.eventsituationtracker.domain.Event;
import com.example.eventsituationtracker.domain.EventType;
import com.example.eventsituationtracker.domain.Severity;
import com.example.eventsituationtracker.repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class EventServiceTest {

    @Autowired
    private EventService eventService;

    @Autowired
    private EventRepository eventRepository;

    @BeforeEach
    void setUp() {
        eventRepository.deleteAll();
    }

    @Test
    void creatingEventShouldPersistIt() {
        Event event = new Event(
            EventType.ALERT,
            Severity.HIGH,
            "Sector A",
            "Test event"
        );

        eventService.createEvent(event);

        List<Event> events = eventRepository.findAll();
        assertThat(events).hasSize(1);
    }
}


