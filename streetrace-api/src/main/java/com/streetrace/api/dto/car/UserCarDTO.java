package com.streetrace.api.dto.car;

import com.streetrace.api.entities.car.CarModel;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCarDTO {
    private Long id;
    private CarModel model;
    // лошадиные силы, могут отличаться от взятой модели, в связи с прокачиваемыми запчастями
    private int power;
    // визуальный тюнинг
    // цвет машины
    private String color;
    // список винилов машины
    private List<UserCarVinylDTO> userCarVinyls = new ArrayList<>();
    // тут также может быть добавлен неон, тонировка, спойлеры итд.
}
