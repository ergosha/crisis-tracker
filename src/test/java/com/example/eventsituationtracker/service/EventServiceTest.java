package com.example.eventsituationtracker.service;
import com.example.eventsituationtracker.domain.Event;
import com.example.eventsituationtracker.domain.Crisis;
import com.example.eventsituationtracker.domain.CrisisEventType;
import com.example.eventsituationtracker.domain.CrisisType;
import com.example.eventsituationtracker.domain.CrisisPriority;
import com.example.eventsituationtracker.domain.Severity;
import com.example.eventsituationtracker.repository.EventRepository;
import com.example.eventsituationtracker.repository.CrisisRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class EventServiceTest {

    @Autowired
    private EventService eventService;

    @Autowired
    private CrisisService crisisService;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private CrisisRepository crisisRepository;

    private Crisis testCrisis;

    @BeforeEach
    void setUp() {
        eventRepository.deleteAll();
        crisisRepository.deleteAll();
        
        // Luodaan test-kriisi
        testCrisis = new Crisis(
            CrisisType.MEDICAL,
            CrisisPriority.URGENT,
            "Test Crisis",
            "Test Location",
            "Test Description"
        );
        testCrisis = crisisService.createCrisis(testCrisis);
    }

    @Test
    void creatingEventShouldPersistIt() {
        Event event = new Event(
            CrisisEventType.DISPATCH,
            Severity.HIGH,
            "Unit dispatched"
        );

        Event created = eventService.createEvent(testCrisis.getId(), event);

        List<Event> events = eventRepository.findAll();
        assertThat(events).hasSize(1);
        assertThat(created.getCrisis().getId()).isEqualTo(testCrisis.getId());
    }

    @Test
    void eventShouldBeLinkToCorrectCrisis() {
        Event event = new Event(
            CrisisEventType.ARRIVED,
            Severity.MEDIUM,
            "Unit arrived at location"
        );

        Event created = eventService.createEvent(testCrisis.getId(), event);

        assertThat(created.getCrisis()).isNotNull();
        assertThat(created.getCrisis().getId()).isEqualTo(testCrisis.getId());
    }

    @Test
    void gettingEventsByCrisisShouldReturnOnlyRelatedEvents() {
        Event event1 = new Event(CrisisEventType.DISPATCH, Severity.HIGH, "Event 1");
        Event event2 = new Event(CrisisEventType.ARRIVED, Severity.MEDIUM, "Event 2");
        
        eventService.createEvent(testCrisis.getId(), event1);
        eventService.createEvent(testCrisis.getId(), event2);

        List<Event> events = eventService.getEventsByCrisis(testCrisis.getId());

        assertThat(events).hasSize(2);
    }

    @Test
    void creatingEventWithNullTypeShouldThrowException() {
        Event event = new Event(null, Severity.HIGH, "Invalid event");

        assertThatThrownBy(() -> eventService.createEvent(testCrisis.getId(), event))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Event type must be provided");
    }

    @Test
    void creatingEventWithInvalidCrisisShouldThrowException() {
        Event event = new Event(CrisisEventType.DISPATCH, Severity.HIGH, "Valid event");
        UUID invalidCrisisId = UUID.randomUUID();

        assertThatThrownBy(() -> eventService.createEvent(invalidCrisisId, event))
            .isInstanceOf(java.util.NoSuchElementException.class)
            .hasMessageContaining("Crisis not found");
    }
}


