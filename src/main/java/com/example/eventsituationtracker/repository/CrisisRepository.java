package com.example.eventsituationtracker.repository;

import com.example.eventsituationtracker.domain.Crisis;
import com.example.eventsituationtracker.domain.CrisisStatus;
import com.example.eventsituationtracker.domain.CrisisPriority;
import com.example.eventsituationtracker.domain.CrisisType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public interface CrisisRepository extends JpaRepository<Crisis, UUID> {

    List<Crisis> findByStatus(CrisisStatus status);

    List<Crisis> findByPriority(CrisisPriority priority);

    List<Crisis> findByType(CrisisType type);

    List<Crisis> findByLocation(String location);

    @Query("SELECT c FROM Crisis c WHERE c.status IN ('OPEN', 'ONGOING') ORDER BY c.priority DESC, c.createdAt ASC")
    List<Crisis> findActiveCrises();

    @Query("SELECT c FROM Crisis c WHERE c.createdAt >= :since ORDER BY c.createdAt DESC")
    List<Crisis> findRecentCrises(@Param("since") Instant since);
}
