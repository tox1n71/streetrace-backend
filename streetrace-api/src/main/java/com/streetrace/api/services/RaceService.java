package com.streetrace.api.services;

import com.streetrace.api.entities.Race;
import com.streetrace.api.entities.User;
import com.streetrace.api.entities.car.UserCar;
import com.streetrace.api.exceptions.NotEnoughFuelException;
import com.streetrace.api.repos.RaceRepository;
import com.streetrace.api.repos.UserCarRepository;
import com.streetrace.api.repos.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class RaceService {
    private final UserRepository userRepository;
    private final UserCarRepository userCarRepository;
    private final RaceRepository raceRepository;

    public Boolean race(Long userId, Long carId, Long friendId) throws NotEnoughFuelException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        if (user.getFuel() < 10){
            throw new NotEnoughFuelException("Not enough fuel");
        }
        User friend = userRepository.findById(friendId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        UserCar userCar = userCarRepository.findById(carId).orElseThrow(()->new EntityNotFoundException("Car not found"));
        UserCar friendCar = friend.getCurrentCar();

        // мощность машин
        int userPower = userCar.getPower();
        int friendPower = friendCar.getPower();
        // шанс победы
        double chance = (userPower - friendPower) / 100.0;
        chance = Math.max(0, Math.min(chance, 1));

        double randomValue = Math.random();
        boolean result = randomValue < chance;
        Race race = Race.builder()
                .user(user)
                .friendCarHorsepower(friendPower)
                .userCarHorsepower(userCar.getPower())
                .userWon(result)
                .friend(friend)
                .build();
        friend.setWasCalledCount(friend.getWasCalledCount() + 1);
        user.setRacesCount(user.getRacesCount() + 1);
        user.setFuel(user.getFuel() - 10);
        if (result){
            user.setWinsCount(user.getWinsCount() + 1);
            user.setMoney(user.getMoney() + 500);
            user.setMoneyWon(user.getMoneyWon() + 500);
        }
        else {
            user.setLosesCount(user.getLosesCount() + 1);
        }

        userRepository.save(user);
        raceRepository.save(race);
        // победа, если случайное число меньше шанса
        return result;
    }
}
