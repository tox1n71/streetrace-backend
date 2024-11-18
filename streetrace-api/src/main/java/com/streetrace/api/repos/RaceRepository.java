package com.streetrace.api.repos;

import com.streetrace.api.entities.Race;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RaceRepository extends JpaRepository<Race, Long> {
}
