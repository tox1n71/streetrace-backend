package com.streetrace.api.services;

import com.streetrace.api.dto.RaceDTO;
import com.streetrace.api.entities.Race;
import com.streetrace.api.entities.User;
import com.streetrace.api.entities.car.UserCar;
import com.streetrace.api.exceptions.NotEnoughFuelException;
import com.streetrace.api.repos.RaceRepository;
import com.streetrace.api.repos.UserCarRepository;
import com.streetrace.api.repos.UserRepository;
import com.streetrace.api.security.JwtService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class RaceService {
    private final UserRepository userRepository;
    private final UserCarRepository userCarRepository;
    private final RaceRepository raceRepository;
    private final JwtService jwtService;

    public Boolean race(String jwtToken, Long carId, Long friendId) throws NotEnoughFuelException {
        Long userId = Long.valueOf(jwtService.extractId(jwtToken));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        if (user.getFuel() < 10){
            throw new NotEnoughFuelException("Not enough fuel");
        }
        User friend = userRepository.findById(friendId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        // TODO: тут желательно добавить проверку, что машина принадлежит пользователю
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
                .raceTime(LocalDateTime.now())
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

    public List<RaceDTO> getLastRacesForUser(String token) {
        Long userId = Long.valueOf(jwtService.extractId(token));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        // Ограничиваем запрос 4 последними гонками
        Pageable pageable = PageRequest.of(0, 4, Sort.by(Sort.Order.desc("raceTime")));
        List<Race> races = raceRepository.findByUserOrderByRaceTimeDesc(user, pageable);

        // Проверяем, что races не равен null
        if (races == null || races.isEmpty()) {
            return new ArrayList<>(); // Возвращаем пустой список, если гонок нет
        }

        // Преобразуем в DTO с помощью Lombok Builder
        return races.stream()
                .map(race -> RaceDTO.builder()
                        .friendName(race.getFriend().getFirstName())  // Предполагаем, что getFriend() и getFirstName() корректны
                        .userWon(race.isUserWon())  // Предполагаем, что метод isUserWon() есть в модели Race
                        .build())
                .collect(Collectors.toList());
    }

}
