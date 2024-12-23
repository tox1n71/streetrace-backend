package com.streetrace.api.repos;

import com.streetrace.api.entities.Race;
import com.streetrace.api.entities.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RaceRepository extends JpaRepository<Race, Long> {
    // Используем Pageable для ограничения числа возвращаемых гонок
    List<Race> findByUserOrderByRaceTimeDesc(User user, Pageable pageable);
}
