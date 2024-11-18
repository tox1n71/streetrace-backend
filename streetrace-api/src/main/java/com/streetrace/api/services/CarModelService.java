package com.streetrace.api.services;

import com.streetrace.api.entities.car.CarModel;
import com.streetrace.api.repos.CarModelRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
/*
 * Сервис для стоковых машинок(да это слово было бы лучше, но кто ж тогда знал. Будет не в падлу поменяю)
 * Получить список, добавить машинку, изменить информацию.
 */
public class CarModelService {
    private CarModelRepository carModelRepository;

    public List<CarModel> getAllCarModels() {
        return carModelRepository.findAll();
    }
}
