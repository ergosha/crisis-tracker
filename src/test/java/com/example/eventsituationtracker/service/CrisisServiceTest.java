package com.example.eventsituationtracker.service;

import com.example.eventsituationtracker.domain.Crisis;
import com.example.eventsituationtracker.domain.CrisisType;
import com.example.eventsituationtracker.domain.CrisisPriority;
import com.example.eventsituationtracker.domain.CrisisStatus;
import com.example.eventsituationtracker.repository.CrisisRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class CrisisServiceTest {

    @Autowired
    private CrisisService crisisService;

    @Autowired
    private CrisisRepository crisisRepository;

    @BeforeEach
    void setUp() {
        crisisRepository.deleteAll();
    }

    @Test
    void creatingCrisisShouldPersistIt() {
        Crisis crisis = new Crisis(
            CrisisType.MEDICAL,
            CrisisPriority.URGENT,
            "Hospital incident",
            "Hospital A",
            "Multiple patients"
        );

        Crisis created = crisisService.createCrisis(crisis);

        assertThat(created.getId()).isNotNull();
        List<Crisis> crises = crisisRepository.findAll();
        assertThat(crises).hasSize(1);
    }

    @Test
    void crisisStatusShouldBeOpenOnCreation() {
        Crisis crisis = new Crisis(
            CrisisType.FIRE,
            CrisisPriority.CRITICAL,
            "Building fire",
            "Downtown",
            "Large commercial building"
        );

        Crisis created = crisisService.createCrisis(crisis);

        assertThat(created.getStatus()).isEqualTo(CrisisStatus.OPEN);
    }

    @Test
    void shouldReturnAllCrises() {
        Crisis crisis1 = new Crisis(CrisisType.FIRE, CrisisPriority.CRITICAL, "Fire 1", "Loc1", "Desc1");
        Crisis crisis2 = new Crisis(CrisisType.MEDICAL, CrisisPriority.URGENT, "Medical 1", "Loc2", "Desc2");

        crisisService.createCrisis(crisis1);
        crisisService.createCrisis(crisis2);

        List<Crisis> crises = crisisService.getAllCrises();

        assertThat(crises).hasSize(2);
    }

    @Test
    void shouldReturnOnlyActiveCrises() {
        Crisis crisis1 = new Crisis(CrisisType.FIRE, CrisisPriority.CRITICAL, "Fire 1", "Loc1", "Desc1");
        Crisis crisis2 = new Crisis(CrisisType.MEDICAL, CrisisPriority.URGENT, "Medical 1", "Loc2", "Desc2");

        Crisis created1 = crisisService.createCrisis(crisis1);
        Crisis created2 = crisisService.createCrisis(crisis2);

        crisisService.updateCrisisStatus(created1.getId(), CrisisStatus.RESOLVED);

        List<Crisis> activeCrises = crisisService.getActiveCrises();

        assertThat(activeCrises).hasSize(1);
        assertThat(activeCrises.get(0).getId()).isEqualTo(created2.getId());
    }

    @Test
    void shouldGetCrisisByStatus() {
        Crisis crisis1 = new Crisis(CrisisType.FIRE, CrisisPriority.CRITICAL, "Fire 1", "Loc1", "Desc1");
        Crisis crisis2 = new Crisis(CrisisType.MEDICAL, CrisisPriority.URGENT, "Medical 1", "Loc2", "Desc2");

        Crisis created1 = crisisService.createCrisis(crisis1);
        crisisService.createCrisis(crisis2);

        crisisService.updateCrisisStatus(created1.getId(), CrisisStatus.ONGOING);

        List<Crisis> ongoingCrises = crisisService.getCrisesByStatus(CrisisStatus.ONGOING);

        assertThat(ongoingCrises).hasSize(1);
        assertThat(ongoingCrises.get(0).getStatus()).isEqualTo(CrisisStatus.ONGOING);
    }

    @Test
    void shouldGetCrisisByLocation() {
        Crisis crisis1 = new Crisis(CrisisType.FIRE, CrisisPriority.CRITICAL, "Fire 1", "Downtown", "Desc1");
        Crisis crisis2 = new Crisis(CrisisType.MEDICAL, CrisisPriority.URGENT, "Medical 1", "Hospital", "Desc2");

        crisisService.createCrisis(crisis1);
        crisisService.createCrisis(crisis2);

        List<Crisis> downtownCrises = crisisService.getCrisesByLocation("Downtown");

        assertThat(downtownCrises).hasSize(1);
        assertThat(downtownCrises.get(0).getLocation()).isEqualTo("Downtown");
    }

    @Test
    void shouldUpdateCrisisStatus() {
        Crisis crisis = new Crisis(CrisisType.FIRE, CrisisPriority.CRITICAL, "Fire", "Loc", "Desc");
        Crisis created = crisisService.createCrisis(crisis);

        crisisService.updateCrisisStatus(created.getId(), CrisisStatus.ONGOING);

        Crisis updated = crisisService.getCrisisById(created.getId());
        assertThat(updated.getStatus()).isEqualTo(CrisisStatus.ONGOING);
    }

    @Test
    void shouldUpdateCrisisPriority() {
        Crisis crisis = new Crisis(CrisisType.FIRE, CrisisPriority.URGENT, "Fire", "Loc", "Desc");
        Crisis created = crisisService.createCrisis(crisis);

        crisisService.updateCrisisPriority(created.getId(), CrisisPriority.CRITICAL);

        Crisis updated = crisisService.getCrisisById(created.getId());
        assertThat(updated.getPriority()).isEqualTo(CrisisPriority.CRITICAL);
    }

    @Test
    void creatingCrisisWithNullTypeShouldThrowException() {
        Crisis crisis = new Crisis(null, CrisisPriority.URGENT, "Title", "Location", "Desc");

        assertThatThrownBy(() -> crisisService.createCrisis(crisis))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Crisis type must be provided");
    }

    @Test
    void creatingCrisisWithNullPriorityShouldThrowException() {
        Crisis crisis = new Crisis(CrisisType.FIRE, null, "Title", "Location", "Desc");

        assertThatThrownBy(() -> crisisService.createCrisis(crisis))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Priority must be provided");
    }

    @Test
    void creatingCrisisWithBlankTitleShouldThrowException() {
        Crisis crisis = new Crisis(CrisisType.FIRE, CrisisPriority.URGENT, "", "Location", "Desc");

        assertThatThrownBy(() -> crisisService.createCrisis(crisis))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Title must not be empty");
    }

    @Test
    void creatingCrisisWithBlankLocationShouldThrowException() {
        Crisis crisis = new Crisis(CrisisType.FIRE, CrisisPriority.URGENT, "Title", "", "Desc");

        assertThatThrownBy(() -> crisisService.createCrisis(crisis))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Location must not be empty");
    }

    @Test
    void gettingNonExistentCrisisShouldThrowException() {
        UUID randomId = UUID.randomUUID();

        assertThatThrownBy(() -> crisisService.getCrisisById(randomId))
            .isInstanceOf(NoSuchElementException.class)
            .hasMessageContaining("Crisis not found");
    }

    @Test
    void deletingCrisisShouldArchiveIt() {
        Crisis crisis = new Crisis(CrisisType.FIRE, CrisisPriority.CRITICAL, "Fire", "Loc", "Desc");
        Crisis created = crisisService.createCrisis(crisis);

        crisisService.deleteCrisis(created.getId());

        Crisis archived = crisisService.getCrisisById(created.getId());
        assertThat(archived.getStatus()).isEqualTo(CrisisStatus.ARCHIVED);
    }

    @Test
    void shouldSetTimestampsCorrectly() {
        Crisis crisis = new Crisis(CrisisType.FIRE, CrisisPriority.CRITICAL, "Fire", "Loc", "Desc");
        Crisis created = crisisService.createCrisis(crisis);

        assertThat(created.getCreatedAt()).isNotNull();
        assertThat(created.getUpdatedAt()).isNotNull();
        assertThat(created.getResolvedAt()).isNull();
        assertThat(created.getClosedAt()).isNull();

        crisisService.updateCrisisStatus(created.getId(), CrisisStatus.RESOLVED);
        Crisis resolved = crisisService.getCrisisById(created.getId());

        assertThat(resolved.getResolvedAt()).isNotNull();
    }

    @Test
    void shouldSetCoordinates() {
        Crisis crisis = new Crisis(CrisisType.FIRE, CrisisPriority.CRITICAL, "Fire", "Loc", "Desc");
        crisis.setCoordinates(60.1699, 24.9384);

        Crisis created = crisisService.createCrisis(crisis);

        assertThat(created.getLatitude()).isEqualTo(60.1699);
        assertThat(created.getLongitude()).isEqualTo(24.9384);
    }
}
