package com.example.eventsituationtracker.service;

import com.example.eventsituationtracker.domain.Crisis;
import com.example.eventsituationtracker.domain.CrisisStatus;
import com.example.eventsituationtracker.domain.CrisisPriority;
import com.example.eventsituationtracker.repository.CrisisRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@Transactional
public class CrisisService {

    private final CrisisRepository crisisRepository;

    public CrisisService(CrisisRepository crisisRepository) {
        this.crisisRepository = crisisRepository;
    }

    public Crisis createCrisis(Crisis crisis) {
        validateCrisis(crisis);
        return crisisRepository.save(crisis);
    }

    @Transactional(readOnly = true)
    public Crisis getCrisisById(UUID id) {
        return crisisRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Crisis not found: " + id));
    }

    @Transactional(readOnly = true)
    public List<Crisis> getAllCrises() {
        return crisisRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Crisis> getActiveCrises() {
        return crisisRepository.findActiveCrises();
    }

    @Transactional(readOnly = true)
    public List<Crisis> getCrisesByStatus(CrisisStatus status) {
        return crisisRepository.findByStatus(status);
    }

    @Transactional(readOnly = true)
    public List<Crisis> getCrisesByLocation(String location) {
        return crisisRepository.findByLocation(location);
    }

    public void updateCrisisStatus(UUID crisisId, CrisisStatus status) {
        Crisis crisis = getCrisisById(crisisId);
        validateStatusTransition(crisis.getStatus(), status);
        crisis.setStatus(status);
        crisisRepository.save(crisis);
    }

    public void updateCrisisPriority(UUID crisisId, CrisisPriority priority) {
        Crisis crisis = getCrisisById(crisisId);
        crisis.setPriority(priority);
        crisisRepository.save(crisis);
    }

    public void updateCrisisDescription(UUID crisisId, String description) {
        Crisis crisis = getCrisisById(crisisId);
        crisis.setDescription(description);
        crisisRepository.save(crisis);
    }

    public void deleteCrisis(UUID id) {
        Crisis crisis = getCrisisById(id);
        // Kriisejä ei poisteta, ne arkistoidaan
        if (crisis.getStatus() != CrisisStatus.ARCHIVED) {
            crisis.setStatus(CrisisStatus.ARCHIVED);
            crisisRepository.save(crisis);
        }
    }

    private void validateCrisis(Crisis crisis) {
        if (crisis.getType() == null) {
            throw new IllegalArgumentException("Crisis type must be provided");
        }
        if (crisis.getPriority() == null) {
            throw new IllegalArgumentException("Priority must be provided");
        }
        if (crisis.getTitle() == null || crisis.getTitle().isBlank()) {
            throw new IllegalArgumentException("Title must not be empty");
        }
        if (crisis.getLocation() == null || crisis.getLocation().isBlank()) {
            throw new IllegalArgumentException("Location must not be empty");
        }
    }

    private void validateStatusTransition(CrisisStatus from, CrisisStatus to) {
        if (from == CrisisStatus.ARCHIVED) {
            throw new IllegalStateException("Cannot change status of archived crisis");
        }
        if (from == CrisisStatus.CLOSED && to != CrisisStatus.ARCHIVED) {
            throw new IllegalStateException("Only ARCHIVED is allowed after CLOSED");
        }
    }
}
