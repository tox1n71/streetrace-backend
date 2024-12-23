package com.streetrace.api.controllers;

import com.streetrace.api.dto.car.UserCarDTO;
import com.streetrace.api.dto.car.VinylRequest;
import com.streetrace.api.exceptions.NotEnoughMoneyException;
import com.streetrace.api.exceptions.ResourceNotFoundException;
import com.streetrace.api.services.UserCarService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
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
    public List<UserCarDTO> getAllUserCars(@RequestHeader("Authorization") String jwtToken) {
        return userCarService.getAllUserCars(jwtToken.substring(7));
    }

    //ручка для покупки машины
    @PostMapping("/buy")
    // id пользователя и id модели машины
    public ResponseEntity<String> buyCar(@RequestHeader("Authorization") String jwtToken, @RequestParam Long carId) {
        try {
            String result = userCarService.buyCar(jwtToken.substring(7), carId);
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
    public ResponseEntity<String> changeCarColor(@RequestHeader("Authorization") String jwtToken, @PathVariable Long id, @RequestParam String color, @RequestParam int cost) {
        try {
            String result = userCarService.changeCarColor(jwtToken.substring(7), id, color, cost);
            return ResponseEntity.ok(result);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (NotEnoughMoneyException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("{carId}/vinyl")
    // id машины пользователя, цвет желаемый, его стоимость
    public ResponseEntity<String> addVinylToCar(@RequestHeader("Authorization") String jwtToken, @PathVariable Long carId, @RequestBody VinylRequest vinylRequest) {
        // В vinylRequest могут быть координаты, размер, угол поворота и идентификатор винила
        try {
            return ResponseEntity.ok(userCarService.addVinylToCar(jwtToken.substring(7), carId, vinylRequest));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}


