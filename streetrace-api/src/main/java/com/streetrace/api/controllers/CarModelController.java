package com.streetrace.api.controllers;


import com.streetrace.api.dto.UserFriendInfoDTO;
import com.streetrace.api.entities.car.CarModel;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.streetrace.api.services.CarModelService;
import java.util.List;

@RestController
@RequestMapping("/api/car-models")
@AllArgsConstructor
// Контроллер стоковых машинок
// p.s. Потомки, кто читает, прошу, не вините, ну я хз не называть же мне модели машин как стоковые машины. Я так посчитал нужным значит так надо было
public class CarModelController {
    private final CarModelService carModelService;

    //ручка для получения списка всех стоковых машинок
    @GetMapping("/get-all-cars")
    public List<CarModel> getFriends() {
        return carModelService.getAllCarModels();
    }
}
