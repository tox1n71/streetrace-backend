package com.streetrace.api.services;

import com.streetrace.api.dto.UserFriendInfoDTO;
import com.streetrace.api.dto.UserFullNameDTO;
import com.streetrace.api.dto.UserResourcesDTO;
import com.streetrace.api.dto.UserStatsDTO;
import com.streetrace.api.entities.*;
import com.streetrace.api.entities.car.CarModel;
import com.streetrace.api.entities.car.UserCar;
import com.streetrace.api.exceptions.NotEnoughMoneyException;
import com.streetrace.api.models.Role;
import com.streetrace.api.repos.CarModelRepository;
import com.streetrace.api.repos.UserCarRepository;
import com.streetrace.api.repos.UserRepository;
import com.streetrace.api.security.JwtService;
import com.streetrace.api.utils.TelegramAuthData;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.*;
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
    private final JwtService jwtService;
    private final UserCarRepository userCarRepository;
    //TODO: добавить логику проверки пользователя
    //TODO: !! Сделать проверку, что если юзер поменял что-то КРОМЕ tgId, то поменять и вбд
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
                    .role(Role.USER)
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
            userRepository.save(user);
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
                    }
                }
                userRepository.save(referrer);
            }
        }

        // Сохраняем пользователя (для нового или обновленного списка друзей)
        userRepository.save(user);


        //генерируем токен
        return jwtService.generateToken(user.getId());
    }

    public boolean isTelegramAuthDataValid(TelegramAuthData data) {
        return true;
    }


    // метод для получения друзей пользователя
    public List<UserFriendInfoDTO> getFriendsInfo(String jwtToken) {
        Long userId = Long.valueOf(jwtService.extractId(jwtToken));
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
    public UserStatsDTO getUserInfo(String jwtToken) {
        Long userId = Long.valueOf(jwtService.extractId(jwtToken));
        System.out.println("PISKA");
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
    public UserResourcesDTO getUserResources(String jwtToken) {
        Long userId = Long.valueOf(jwtService.extractId(jwtToken));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        return UserResourcesDTO.builder()
                .money(user.getMoney())
                .level(user.getLevel())
                .fuel(user.getFuel())
                .currentCarPower(user.getCurrentCar().getPower())
                .build();
    }

    public String updateCurrentCar(String token, Long carId){
        Long userId = Long.valueOf(jwtService.extractId(token));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        UserCar newCurrentCar = userCarRepository.findById(carId)
                .orElseThrow(() -> new EntityNotFoundException("UserCar not found"));

        if (!user.getUserCars().contains(newCurrentCar)) {
            throw new IllegalArgumentException("Угон запрещен на этом сервере");
        }
        user.setCurrentCar(newCurrentCar);
        userRepository.save(user);
        return "The " + newCurrentCar.getModel().getBrand() + " knew exactly that they want";
    }
    public String buyFuel(String token){
        Long userId = Long.valueOf(jwtService.extractId(token));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        if (user.getMoney() < 3000){
            throw new NotEnoughMoneyException("Я тут циферки прикинул... мы это просто не потянем... я не волшебник");
        }
        user.setMoney(user.getMoney() - 3000);
        user.setFuel(100);
        userRepository.save(user);
        return "полный 95-го залили";
    }
    public String generateInvitationLink(String token){
        Long userId = Long.valueOf(jwtService.extractId(token));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        return "t.me/streetracevkbot/streetraceapp?startapp=racerId_" + user.getTelegramId();
    }

    public UserFullNameDTO getFullName(String token){
        Long userId = Long.valueOf(jwtService.extractId(token));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        UserFullNameDTO fullNameDTO = UserFullNameDTO.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
        return fullNameDTO;
    }

}