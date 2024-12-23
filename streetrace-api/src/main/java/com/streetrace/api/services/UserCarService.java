package com.streetrace.api.services;

import com.streetrace.api.dto.car.UserCarDTO;
import com.streetrace.api.dto.car.VinylRequest;
import com.streetrace.api.entities.User;
import com.streetrace.api.entities.car.CarModel;
import com.streetrace.api.entities.car.UserCar;
import com.streetrace.api.entities.car.UserCarVinyl;
import com.streetrace.api.entities.car.Vinyl;
import com.streetrace.api.exceptions.NotEnoughMoneyException;
import com.streetrace.api.exceptions.ResourceNotFoundException;
import com.streetrace.api.repos.CarModelRepository;
import com.streetrace.api.repos.UserCarRepository;
import com.streetrace.api.repos.UserRepository;
import com.streetrace.api.repos.VinylRepository;
import com.streetrace.api.security.JwtService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;

import static com.streetrace.api.mappers.UserCarMapper.convertToUserCarDTO;

@Service
@AllArgsConstructor
public class UserCarService {
    private final UserCarRepository userCarRepository;
    private final UserRepository userRepository;
    private final CarModelRepository carModelRepository;
    private final JwtService jwtService;
    private final VinylRepository vinylRepository;

    public List<UserCarDTO> getAllUserCars(String jwtToken) {
        Long userId = Long.valueOf(jwtService.extractId(jwtToken));
        List<UserCar> userCars = userCarRepository.findByUserId(userId);
        List<UserCarDTO> userCarDTOs = new ArrayList<>();
        for (UserCar userCar : userCars) {
            userCarDTOs.add(convertToUserCarDTO(userCar));
        }
        return userCarDTOs;
    }

    public String buyCar(String jwtToken, Long carModelId) throws NotEnoughMoneyException {
        Long userId = Long.valueOf(jwtService.extractId(jwtToken));
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

    public String changeCarColor(String jwtToken, Long userCarId, String color, int colorCost) throws NotEnoughMoneyException {
        Long userId = Long.valueOf(jwtService.extractId(jwtToken));
        User user = userRepository.findById(userId)
                .orElseThrow(EntityNotFoundException::new);
        UserCar userCar = userCarRepository.findById(userCarId).
                orElseThrow(() -> new ResourceNotFoundException("Машина пользователя не найдена"));
        if (!userCar.getUser().getId().equals(userId)) {
            throw new NotEnoughMoneyException("Угон запрещен на этом сервере");
        }
        if (userCar.getUser().getMoney() < colorCost) {
            throw new NotEnoughMoneyException("Кыш, нищук!");
        }
        userCar.getUser().setMoney(userCar.getUser().getMoney() - colorCost);
        userCar.setColor(color);
        userCarRepository.save(userCar);
        userRepository.save(user);
        return "Не бит, не крашен(ну типа ) )";
    }

    public String addVinylToCar(String token, Long carId, VinylRequest vinylRequest) {
        // Логика добавления винила на машину
        Long userId = Long.valueOf(jwtService.extractId(token));
        User user = userRepository.findById(userId)
                .orElseThrow(EntityNotFoundException::new);
        UserCar userCar = userCarRepository.findById(carId)
                .orElseThrow(() -> new EntityNotFoundException("User car not found"));
        if (!userCar.getUser().getId().equals(userId)) {
            throw new NotEnoughMoneyException("Угон запрещен на этом сервере");
        }
        Vinyl vinyl = vinylRepository.findById(vinylRequest.getVinylId())
                .orElseThrow(() -> new EntityNotFoundException("Vinyl not found"));
        if (user.getMoney() < vinyl.getPrice()) {
            throw new NotEnoughMoneyException("Кыш, нищук");
        }
        user.setMoney(user.getMoney() - vinyl.getPrice());
        // Добавляем винил на машину
        UserCarVinyl userCarVinyl = UserCarVinyl.builder()
                .x(vinylRequest.getX())
                .y(vinylRequest.getY())
                .scale(vinylRequest.getScale())
                .rotationAngle(vinylRequest.getRotationAngle())
                .vinyl(vinyl)
                .userCar(userCar)
                .build();
        userCar.getUserCarVinyls().add(userCarVinyl);
        userCarRepository.save(userCar);
        userRepository.save(user);
        return "Теперь точно гоночная";
    }
}
