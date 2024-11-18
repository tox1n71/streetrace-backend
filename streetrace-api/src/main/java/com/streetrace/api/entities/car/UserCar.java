package com.streetrace.api.entities.car;

import com.streetrace.api.entities.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.ArrayList;

@Builder
@AllArgsConstructor
@Entity
@Getter
@Setter
@NoArgsConstructor
public class UserCar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "car_id")
    private CarModel model;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    // лошадиные силы могут отличаться от взятой модели в связи с прокачиваемыми запчастями
    private int power;

    // визуальный тюнинг
    private String color;
    @OneToMany(mappedBy = "userCar", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserCarVinyl> userCarVinyls = new ArrayList<>();
    // тут также может быть добавлен неон, тонировка, спойлеры итд.

    // прокачиваемые запчасти
    private int tiresLevel;
    private int brakesLevel;
    private int absorberLevel; //амортизаторы
    private int exhaustLevel;
    private int intercoolerLevel;

}
