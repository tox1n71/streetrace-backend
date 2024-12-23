package com.streetrace.api.repos;

import com.streetrace.api.entities.car.Vinyl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VinylRepository extends JpaRepository<Vinyl, Long> {
}
