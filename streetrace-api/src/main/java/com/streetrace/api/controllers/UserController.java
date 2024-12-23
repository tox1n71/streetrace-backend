package com.streetrace.api.controllers;

import com.streetrace.api.dto.UserFriendInfoDTO;
import com.streetrace.api.dto.UserFullNameDTO;
import com.streetrace.api.dto.UserResourcesDTO;
import com.streetrace.api.dto.UserStatsDTO;
import com.streetrace.api.utils.TelegramAuthData;
import com.streetrace.api.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    //ручка для сохранения и авторизации пользователя
    @PostMapping("/login")
    public ResponseEntity<String> authenticateUser(
            @RequestBody TelegramAuthData telegramData,
            @RequestParam(required = false) Long racerId) {
        try {
            String response = userService.authenticate(telegramData, racerId);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid Telegram Auth Data");
        }
    }

    // ручка, чтобы достать друзей пользователя, таких же пользователей (их имя, фамилия, а также текущая машина)
    @GetMapping("/get-friends")
    public List<UserFriendInfoDTO> getFriends(@RequestHeader("Authorization") String jwtToken) {
        return userService.getFriendsInfo(jwtToken.substring(7));
    }

    // Ручка для получения информации о текущем пользователе
    @GetMapping("/get-stats")
    public UserStatsDTO getUserStats(@RequestHeader("Authorization") String jwtToken) {
        return userService.getUserInfo(jwtToken.substring(7));
    }


    // Ручка для двери в спальню твоей матери. Шучу ручка для получения ресурсов пользователя
    @GetMapping("/get-resources")
    public UserResourcesDTO getUserResources(@RequestHeader("Authorization") String jwtToken) {
        return userService.getUserResources(jwtToken.substring(7));
    }

    // ручка для установления другой машины текущей
    @PostMapping("/update-current-car")
    public ResponseEntity<String> updateCurrentCar(@RequestHeader("Authorization") String jwtToken, @RequestParam Long carId) {
        return ResponseEntity.ok(userService.updateCurrentCar(jwtToken.substring(7), carId));
    }

    // ручка для покупки бензина
    @PostMapping("/buy-fuel")
    public ResponseEntity<String> buyFuel(@RequestHeader("Authorization") String jwtToken) {
        return ResponseEntity.ok(userService.buyFuel(jwtToken.substring(7)));
    }

    @GetMapping("/generate-invitation-link")
    public ResponseEntity<String> generateInvitationLink(@RequestHeader("Authorization") String jwtToken) {
            return ResponseEntity.ok(userService.generateInvitationLink(jwtToken.substring(7)));
    }
    @GetMapping("/fullName")
    public ResponseEntity<UserFullNameDTO> getFullName(@RequestHeader("Authorization") String jwtToken) {
        return ResponseEntity.ok(userService.getFullName(jwtToken.substring(7)));
    }
}