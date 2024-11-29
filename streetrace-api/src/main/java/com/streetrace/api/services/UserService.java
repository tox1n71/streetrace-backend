package com.streetrace.api.services;

import com.streetrace.api.dto.UserFriendInfoDTO;
import com.streetrace.api.dto.UserResourcesDTO;
import com.streetrace.api.dto.UserStatsDTO;
import com.streetrace.api.entities.*;
import com.streetrace.api.entities.car.CarModel;
import com.streetrace.api.entities.car.UserCar;
import com.streetrace.api.repos.CarModelRepository;
import com.streetrace.api.repos.UserRepository;
import com.streetrace.api.utils.TelegramAuthData;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.streetrace.api.mappers.UserCarMapper.convertToUserCarDTO;

@Service
@AllArgsConstructor
/*
 * Сервис с логикой для пользователя. Достает всякую информацию, добавляет, авторизует и регистрирует
 */
public class UserService {
    private final UserRepository userRepository;
    private final CarModelRepository carModelRepository;

    //TODO: добавить логику проверки пользователя

    public String authenticate(TelegramAuthData telegramData, Long referralId) {
        if (!isTelegramAuthDataValid(telegramData)) {
            throw new IllegalArgumentException("Invalid Telegram Auth Data");
        }

        // Ищем пользователя по Telegram ID
        Optional<User> existingUser = userRepository.findByTelegramId(telegramData.getTelegramId());

        User user;
        boolean isNewUser = false;

        if (existingUser.isPresent()) {
            // Пользователь существует
            user = existingUser.get();
        } else {
            // Регистрируем нового пользователя
            isNewUser = true;
            user = User.builder()
                    .telegramId(telegramData.getTelegramId())
                    .username(telegramData.getUsername())
                    .firstName(telegramData.getFirstName())
                    .lastName(telegramData.getLastName())
                    .money(4200) // стартовые деньги
                    .fuel(100) // стартовый бензин
                    .friends(new ArrayList<>()) // Инициализируем список друзей
                    .userCars(new ArrayList<>()) // Инициализируем список машин
                    .build();

            // Добавляем стартовую машину
            CarModel defaultCarModel = carModelRepository.findById(1L)
                    .orElseThrow(() -> new IllegalStateException("Default car model not found"));

            UserCar userCar = UserCar.builder()
                    .model(defaultCarModel)
                    .user(user)
                    .color(defaultCarModel.getDefaultColor())
                    .power(defaultCarModel.getPower())
                    .build();

            user.getUserCars().add(userCar);
            user.setCurrentCar(userCar);
        }

        // Логика работы с реферальным ID
        if (referralId != null) {
            Optional<User> referrerOpt = userRepository.findByTelegramId(referralId);
            if (referrerOpt.isPresent()) {
                User referrer = referrerOpt.get();

                // Проверяем, что пользователи еще не являются друзьями
                if (!user.getFriends().contains(referrer)) {
                    // Добавляем друг другу в друзья
                    user.getFriends().add(referrer);
                    referrer.getFriends().add(user);

                    // Если пользователь новый, начисляем бонусы
                    if (isNewUser) {
                        user.setMoney(user.getMoney() + 1000);
                        referrer.setMoney(referrer.getMoney() + 1000);

                        // Сохраняем изменения у реферера
                        userRepository.save(referrer);
                    }
                }
            }
        }

        // Сохраняем пользователя (для нового или обновленного списка друзей)
        userRepository.save(user);

        return "user.toString()";
    }

    private boolean isTelegramAuthDataValid(TelegramAuthData data) {
        // Тут проверка подписи от Telegram
        return true; // Временный ответ для примера
    }


    // метод для получения друзей пользователя
    public List<UserFriendInfoDTO> getFriendsInfo(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        List<User> friends = user.getFriends();

        return friends.stream().map(friend ->
                UserFriendInfoDTO.builder()
                        .id(friend.getId())
                        .telegramId(friend.getTelegramId())
                        .firstName(friend.getFirstName())
                        .lastName(friend.getLastName())
                        .winsCount(friend.getWinsCount())
                        .losesCount(friend.getLosesCount())
                        .level(friend.getLevel())
                        .currentCar(convertToUserCarDTO(friend.getCurrentCar()))
                        .build()
        ).collect(Collectors.toList());
    }



    // метод для получения информации о текущем пользователе
    public UserStatsDTO getUserInfo(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        return new UserStatsDTO(
                user.getRacesCount(),
                user.getWinsCount(),
                user.getLosesCount(),
                user.getGarageCapacity(),
                user.getMoneySpend(),
                user.getMoneyWon(),
                user.getWasCalledCount(),
                user.getFuelTankLevel()
        );
    }
    public UserResourcesDTO getUserResources(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        return UserResourcesDTO.builder()
                .money(user.getMoney())
                .level(user.getLevel())
                .fuel(user.getFuel())
                .currentCarPower(user.getCurrentCar().getPower())
                .build();
    }
}