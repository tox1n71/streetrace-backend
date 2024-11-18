package com.streetrace.api.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
// для передачи на фронт данных пользователя
public class UserResourcesDTO {
    // информация
    // деньги
    private int money;
    // бензин
    private int fuel;
    // уровень
    private int level;
    // лошадиные силы текущей машины
    private int currentCarPower;
}