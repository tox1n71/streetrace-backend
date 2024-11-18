package com.streetrace.api.services;

import com.streetrace.api.dto.car.UserCarDTO;
import com.streetrace.api.entities.User;
import com.streetrace.api.entities.car.CarModel;
import com.streetrace.api.entities.car.UserCar;
import com.streetrace.api.exceptions.NotEnoughMoneyException;
import com.streetrace.api.exceptions.ResourceNotFoundException;
import com.streetrace.api.repos.CarModelRepository;
import com.streetrace.api.repos.UserCarRepository;
import com.streetrace.api.repos.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.streetrace.api.mappers.UserCarMapper.convertToUserCarDTO;

@Service
@AllArgsConstructor
public class UserCarService {
    private final UserCarRepository userCarRepository;
    private final UserRepository userRepository;
    private final CarModelRepository carModelRepository;
    public List<UserCarDTO> getAllUserCars(Long userId) {
        List<UserCar> userCars = userCarRepository.findByUserId(userId);
        List<UserCarDTO> userCarDTOs = new ArrayList<>();
        for (UserCar userCar : userCars) {
            userCarDTOs.add(convertToUserCarDTO(userCar));
        }
        return userCarDTOs;
    }

    public String buyCar(Long userId, Long carModelId) throws NotEnoughMoneyException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден"));
        // Получаем модель машины
        CarModel carModel = carModelRepository.findById(carModelId)
                .orElseThrow(() -> new ResourceNotFoundException("Модель машины не найдена"));
        if (user.getMoney() < carModel.getPrice()) {
            throw new NotEnoughMoneyException("Кыш, нищук!");
        }
        user.setMoney(user.getMoney() - carModel.getPrice());
        // Создаем новую машину для пользователя
        UserCar userCar = UserCar.builder()
                .color(carModel.getDefaultColor())
                .model(carModel)
                .user(user)
                .power(carModel.getPower())
                .build();
        // Обновляем пользователя (добавляем машину в его гараж) P.S. из-за CascadeType.ALL по идеи можно сохранить просто юзера, он сам сохранит уже машину
        user.getUserCars().add(userCar);
        userRepository.save(user);
        return "За деньги да!";
    }

    public String changeCarColor(Long userCarId, String color, int colorCost) throws NotEnoughMoneyException {
        UserCar userCar = userCarRepository.findById(userCarId).
                orElseThrow(() -> new ResourceNotFoundException("Машина пользователя не найдена"));
        if (userCar.getUser().getMoney() < colorCost) {
            throw new NotEnoughMoneyException("Кыш, нищук!");
        }
        userCar.getUser().setMoney(userCar.getUser().getMoney() - colorCost);
        userCar.setColor(color);
        userCarRepository.save(userCar);
        return "Не бит, не крашен(ну типа ) )";
    }
}
