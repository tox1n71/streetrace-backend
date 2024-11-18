package com.streetrace.api.repos;

import com.streetrace.api.entities.car.UserCar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserCarRepository extends JpaRepository<UserCar, Long> {
    List<UserCar> findByUserId(Long user_id);
}