package com.streetrace.api.repos;

import com.streetrace.api.entities.car.CarModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarModelRepository extends JpaRepository<CarModel, Long> {
}
