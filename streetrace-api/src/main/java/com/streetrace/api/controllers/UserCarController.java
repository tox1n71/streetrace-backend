package com.streetrace.api.controllers;

import com.streetrace.api.dto.car.UserCarDTO;
import com.streetrace.api.exceptions.NotEnoughMoneyException;
import com.streetrace.api.exceptions.ResourceNotFoundException;
import com.streetrace.api.services.UserCarService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-cars")
@AllArgsConstructor
public class UserCarController {
    private final UserCarService userCarService;
    // ручка, чтобы достать все автомобили пользователя
    // туду: переделать на экстракт токен, как и везде, потому что я ленивый очоч:((
    @GetMapping
    public List<UserCarDTO> getAllUserCars(@RequestParam long userId) {
        return userCarService.getAllUserCars(userId);
    }

    //ручка для покупки машины
    @PostMapping("/buy")
    // id пользователя и id модели машины
    public ResponseEntity<String> buyCar(@RequestParam Long userId, @RequestParam Long carId) {
        try {
            String result = userCarService.buyCar(userId, carId);
            return ResponseEntity.ok(result);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (NotEnoughMoneyException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    //ручка для покраски машины
    @PostMapping("{id}/color")
    // id машины пользователя, цвет желаемый, его стоимость
    public ResponseEntity<String> changeCarColor(@PathVariable Long id, @RequestParam String color, @RequestParam int cost) {
        try {
            String result = userCarService.changeCarColor(id, color, cost);
            return ResponseEntity.ok(result);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (NotEnoughMoneyException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
