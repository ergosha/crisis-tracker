package com.example.eventsituationtracker.controller;

import com.example.eventsituationtracker.domain.Crisis;
import com.example.eventsituationtracker.domain.CrisisStatus;
import com.example.eventsituationtracker.dto.CreateCrisisRequest;
import com.example.eventsituationtracker.dto.UpdateCrisisRequest;
import com.example.eventsituationtracker.dto.CrisisResponse;
import com.example.eventsituationtracker.service.CrisisService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/crises")
public class CrisisController {

    private final CrisisService crisisService;

    public CrisisController(CrisisService crisisService) {
        this.crisisService = crisisService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CrisisResponse createCrisis(@Valid @RequestBody CreateCrisisRequest request) {
        Crisis crisis = new Crisis(
            request.getType(),
            request.getPriority(),
            request.getTitle(),
            request.getLocation(),
            request.getDescription()
        );
        if (request.getLatitude() != null && request.getLongitude() != null) {
            crisis.setCoordinates(request.getLatitude(), request.getLongitude());
        }
        Crisis created = crisisService.createCrisis(crisis);
        return new CrisisResponse(created);
    }

    @GetMapping
    public List<CrisisResponse> getAllCrises() {
        return crisisService.getAllCrises()
            .stream()
            .map(CrisisResponse::new)
            .toList();
    }

    @GetMapping("/active")
    public List<CrisisResponse> getActiveCrises() {
        return crisisService.getActiveCrises()
            .stream()
            .map(CrisisResponse::new)
            .toList();
    }

    @GetMapping("/{id}")
    public CrisisResponse getCrisisById(@PathVariable UUID id) {
        return new CrisisResponse(crisisService.getCrisisById(id));
    }

    @GetMapping("/status/{status}")
    public List<CrisisResponse> getCrisesByStatus(@PathVariable CrisisStatus status) {
        return crisisService.getCrisesByStatus(status)
            .stream()
            .map(CrisisResponse::new)
            .toList();
    }

    @GetMapping("/location/{location}")
    public List<CrisisResponse> getCrisesByLocation(@PathVariable String location) {
        return crisisService.getCrisesByLocation(location)
            .stream()
            .map(CrisisResponse::new)
            .toList();
    }

    @PutMapping("/{id}")
    public CrisisResponse updateCrisis(
        @PathVariable UUID id,
        @Valid @RequestBody UpdateCrisisRequest request
    ) {
        if (request.getStatus() != null) {
            crisisService.updateCrisisStatus(id, request.getStatus());
        }
        if (request.getPriority() != null) {
            crisisService.updateCrisisPriority(id, request.getPriority());
        }
        if (request.getDescription() != null) {
            crisisService.updateCrisisDescription(id, request.getDescription());
        }
        return new CrisisResponse(crisisService.getCrisisById(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCrisis(@PathVariable UUID id) {
        crisisService.deleteCrisis(id);
    }
}
